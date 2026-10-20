# CDA display tool

The eHDSI CDA display tool: XSLT 2.0 stylesheets that render CDA documents (ePrescription and Patient Summary) as HTML.
`CdaDisplayTransformer` runs them, through the portal's `portal.xsl` (see below).

Copied from OpenNCP, <https://code.europa.eu/ehdsi/ehealth>:

- Path: `openncp-core/openncp-core-client-connector/src/main/resources/` (`cda.xsl`, `common/`, `ep/`, `ps/`, `css/`)
- Version: OpenNCP 9.1.0, commit `5d4787134`

`ps/` isn't used by the portal, but it is kept since `cda.xsl` imports it.

The stylesheets look up labels and code translations in the value sets in `../value-sets/value-sets.zip`, see "Value
sets for the CDA display tool" in the project README.

## Local changes

Keep the upstream `.xsl` files as they are, so an update is a copy. Changes to the rendering go in `portal.xsl`, which
imports `cda.xsl` and overrides its templates. The CSS may be changed directly, since it's inlined into the output
(each file is wrapped in `<root><![CDATA[...]]></root>` so `document()` can read it).

### `portal.xsl`

- The ePrescription's narrative (the sections' `<text>`) is shown in a collapsed "Original narrative" panel below the
  coded content.

  Upstream only shows the narrative for the Patient Summary, where each section has such a panel. For the
  ePrescription it renders the coded content, and only uses the narrative for text that coded elements reference, such
  as the instructions to the patient. That fits the eHDSI model, where the pharmacist works from the coded and
  translated content, and the original rendering is the L1 PDF. We show the narrative anyway, as a cross-check for
  when the coding or translation is incomplete.

  The panel uses the Patient Summary's narrative template and label, and the same markup as the Patient and Prescriber
  panels. The override of `epCda` has to repeat upstream's `epCda` (a template that overrides a named template can't
  call it), and `CdaDisplayTransformerTest` checks that the copy still matches upstream.

  The `shownarrative` parameter in `common/cdaCommon.xsl` looks like it would do this, but no stylesheet uses it.

### CSS

- `css/style.css`, `css/ep.css`: the Montserrat font, which is loaded from Google Fonts, is replaced with the portal's
  font stack. The portal's CSP blocks the external fonts.
- `css/style.css`: removed the `@import` of `ps.css` and `ep.css`. They are inlined, and the imports resolved to 404s.
- `css/style.css`: the `fa-exclamation-circle` icon, which marks missing information, is drawn as text, since
  FontAwesome isn't loaded.
- `css/style.css`: the dispensation form is hidden. The stylesheets always render it, and the `allowDispense` parameter
  only hides its submit button.
- `css/style.css`: hidden tooltips take no space (`display: none`, instead of only being invisible), since the ones next
  to the right edge made the page scroll horizontally.

## Updating

1. Copy the files listed above from the new OpenNCP version over these, and update the version above.
2. Reapply the local changes to the CSS: `git diff` against the previous upstream version shows them.
3. If upstream's `epCda` has changed, the `CdaDisplayTransformerTest` fails. Update the copy in `portal.xsl`.
4. If the stylesheets reference new value sets, the `CdaDisplayTransformerTest` fails. Run
   `scripts/update-value-sets.sh` (see the project README).
