import os
import pandas as pd
import re
import csv
from typing import Iterable

### # Convert LMS fixed-width record files from ftp.medicinpriser.dk to CSV.
###
### This script is made to support semantics in getting usable CSV files for
### working with the data from "Taksten".  We also generate code system concept
### version files for uploading code systems to CTS, as specified in:
### https://webgate.ec.europa.eu/fpfis/wikis/pages/viewpage.action?spaceKey=EHDSI&title=Creation+of+National+Code+System+file+to+be+uploaded+to+the+CTS+v2.1
###
### ## Prerequisites:
###
### To run the script you need Python 3, and some dependencies. To install the dependencies we recommend using virtual environments:
###
###    python3 -m venv venv
###    cd venv/Scripts && activate && cd ../../ # For Windows users
###    source venv/bin/activate                 # For Linux/MacOS/etc. users
###    pip3 install -r requirements.txt
###
### ## Usage:
###
### You need the lmsXX.txt files and system.txt from
### ftp.medicinpriser.dk, as well as a schema file ("databeskrivelse.xslx") (see
### https://laegemiddelstyrelsen.dk/da/tilskud/priser/medicinpriser-for-erhverv/eksempel-paa-medicinprisfiler/)
### Run the script with the following command:
###
###     python3 convert_lms.py <path to directory with lms txt files> databeskrivelse.xslx
###

def parse_system_file(system_file):
    """Extracts LMS file names from system.txt"""
    lms_files = []
    with open(system_file, 'r', encoding='cp850') as f:
        for line in f:
            match = re.search(r'(LMS\d+\.txt)', line)
            if match:
                lms_files.append(match.group(1))
    return lms_files

def get_schema_for_file(excel_file, lms_file):
    """Extracts the field schema for a given LMS file from the spreadsheet."""
    df = pd.read_excel(excel_file, sheet_name='Nyt')
    df = df.dropna(how='all').reset_index(drop=True)

    # Find where the schema for the given file starts
    schema_start_idx = df[df.iloc[:, 0].astype(str).str.contains(lms_file.replace('.txt', ''), na=False)].index
    if schema_start_idx.empty:
        return None

    schema_start_idx = schema_start_idx[0]
    schema_end_idx = schema_start_idx + 1
    while schema_end_idx < len(df):
        cell_value = str(df.iloc[schema_end_idx, 0])
        # Stop if we find total length marker or next LMS definition
        if (cell_value.startswith("Total record-længde") or
            (cell_value.startswith("LMS") and cell_value != lms_file.replace('.txt', ''))):
            break
        schema_end_idx += 1

    schema = []
    for idx in range(schema_start_idx + 1, schema_end_idx):
        row = df.iloc[idx]
        field_name = str(row.iloc[2]).strip()  # Column C for field name
        position_str = str(row.iloc[5]).strip()  # Column F for positions

        # Ensure position_str is valid before parsing
        if position_str.lower() == 'nan' or position_str.strip() == "":
            continue

        # Extract start and end positions
        if '-' in position_str:
            start, end = map(int, position_str.split('-'))
        else:
            start = end = int(position_str)
        length = end - start + 1

        # Special case for Administrationsvej in LMS01.txt
        if lms_file.lower() == "lms01.txt" and field_name == "Administrationsvej":
            # Split into 4 fields of length 2
            for i in range(4):
                field_name_i = f"Administrationsvej {i+1}"
                start_i = start + (i * 2)
                schema.append((field_name_i, start_i - 1, 2))  # Convert 1-based to 0-based index
            continue

        # Sanity check: Compare calculated length with column E
        expected_length = str(row.iloc[4]).strip()  # Column E
        if expected_length.lower() != 'nan' and expected_length.strip():
            try:
                expected_length = int(expected_length)
                if length != expected_length:
                    raise ValueError(f"Length mismatch in {lms_file} row {idx+1}: "
                                  f"Calculated length {length} doesn't match "
                                  f"expected length {expected_length} for field {field_name}")
            except ValueError as e:
                if "Length mismatch" in str(e):
                    raise
                # If error was due to int conversion, just skip the check
                pass

        if field_name:
            schema.append((field_name, start - 1, length))  # Convert 1-based to 0-based index

    return schema

def generate_code_system_version_concepts(output_dir: str, lms_numbers: Iterable[str]):
    """Creates code system version concept files for the given LMS CSVs."""
    for number in lms_numbers:
        input_csv = os.path.join(output_dir, f"LMS{number}.csv")
        if not os.path.exists(input_csv):
            print(f"Skipping LMS{number}: CSV not found at {input_csv}.")
            continue

        df = pd.read_csv(input_csv)
        missing_columns = {"Kode", "Tekst"} - set(df.columns)
        if missing_columns:
            missing_list = ", ".join(sorted(missing_columns))
            print(f"Skipping LMS{number}: missing required columns {missing_list}.")
            continue

        subset = df.loc[:, ["Kode", "Tekst"]].copy()
        subset["Kode"] = subset["Kode"].where(subset["Kode"].notna(), "").astype(str).str.strip()
        subset["Tekst"] = subset["Tekst"].where(subset["Tekst"].notna(), "").astype(str).str.strip()
        subset = subset[(subset["Kode"] != "") & (subset["Tekst"] != "")]
        subset = subset.drop_duplicates()

        output_path = os.path.join(output_dir, f"lms{number}-code-system-version-concept.csv")
        subset.to_csv(output_path, sep='|', header=False, index=False, encoding='utf-8', quoting=csv.QUOTE_MINIMAL)
        print(f"Generated code system version concept file for LMS{number}: {output_path}")

def convert_fixed_to_csv(input_file, output_file, schema):
    """Reads a fixed-length LMS file and writes it as CSV."""
    with open(input_file, 'r', encoding='cp850') as f, \
         open(output_file, 'w', encoding='utf-8', newline='') as out_f:
        writer = csv.writer(out_f)

        # Write headers
        headers = [field[0] for field in schema]
        writer.writerow(headers)

        # Write data
        for line in f:
            values = [line[start:start+length].strip() for _, start, length in schema]
            writer.writerow(values)

def process_lms_files(directory, schema_file):
    """Processes all LMS files based on the system.txt file."""
    system_file = os.path.join(directory, 'system.txt')
    if not os.path.exists(system_file):
        print("system.txt not found in directory.")
        return

    lms_files = parse_system_file(system_file)
    output_dir = os.path.join(directory, "csv")
    if not os.path.exists(output_dir):
        os.makedirs(output_dir)

    for lms_file in lms_files:
        schema = get_schema_for_file(schema_file, lms_file)
        if schema:
            input_path = os.path.join(directory, lms_file)
            output_path = os.path.join(output_dir, lms_file.replace('.txt', '.csv'))
            convert_fixed_to_csv(input_path, output_path, schema)
            print(f"Converted {lms_file} to CSV.")
        else:
            print(f"No schema found for {lms_file}.")

    generate_code_system_version_concepts(output_dir, ["11", "14", "15", "22"])

if __name__ == "__main__":
    import sys
    if len(sys.argv) < 3:
        print("Usage: python convert_lms.py <directory> <schema_file.xlsx>")
    else:
        process_lms_files(sys.argv[1], sys.argv[2])
    # process_lms_files(".", "databeskrivelse.xlsx")
