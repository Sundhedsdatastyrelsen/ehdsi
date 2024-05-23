#!/usr/bin/env bash

#
# This script can be used to run the epps backend application from a commandline.
# See documentation in README.md
#


projectDir=$(readlink -f $(dirname "$0"))
profiles="dev"
cd $projectDir

function maven_build() {
		echo -e "\e[0;34mmvn -B $@\e[0m\n"
		mvn --color=always -B $@ | grep --color=never -E 'Building.*\[|SUCCESS|FAIL|ERROR|-<|\] Test|Result|Total'
		exit_code=${PIPESTATUS[0]}
		if [ $exit_code != 0 ]; then
			echo -e "\n\e[1;31mRun 'mvn -B $@' manually to see detailed errors\e[0m\n"
			exit $exit_code
		fi
}

function load_env() {
	if [ ! -f .env ]; then
		echo -e "\e[0;34mNo '.env' file exists, creating default '.env' file.\e[0m\n"
		cp .env.defaults .env
	fi

	export $(grep -v '^#' .env | xargs -d '\n')
}

# determine if we should call "docker-compose" or "docker compose"
compose="docker compose"
if ! $(docker compose > /dev/null 2>&1); then
	if ! compose=$(which "docker-compose"); then
		echo "Could not find 'docker compose' command"
		exit 1
	fi
fi

cd $projectDir
while [[ $# -gt 0 ]]; do
	option="$1"
	shift

	case "$option" in
		build)
			echo -e "\n\e[1;33m* Building with maven: \e[32m$projectDir\e[0m\n"
			maven_build clean package
		;;
		run)
			echo -e "\n\e[1;33m* Running app: \e[32m$projectDir\e[0m\n"
			jarfile="epps-application/target/epps-application.jar"
			if [ ! -f "$jarfile" ]; then
				echo -e "\n\e[1;31mJar file '$jarfile' not found, please build first.\e[0m\n"
				exit 1
			fi
			load_env
			java -jar "$jarfile"
		;;
		start|up)
			echo -e "\n\e[1;33m* Starting containers\e[0m\n"
			load_env
			$compose --file docker-compose.local.yml --env-file=.env up -d -V --build
		;;
		startdebug|debug)
			export JAVA_DEBUG="true"
			echo -e "\n\e[1;33m* Starting containers in debug mode\e[0m\n"
			load_env
			$compose --file docker-compose.local.yml up --env-file=.env -d -V --build
		;;
		restart)
			echo -e "\n\e[1;33m* Restarting containers:\e[0m\n"
			load_env
			$compose --file docker-compose.local.yml kill epps-country-a && $compose --file docker-compose.local.yml --env-file=.env up -d -V --build epps-country-a
		;;
		log|logs)
			echo -e "\n\e[1;33m* Tailing logs for containers:\e[0m\n"
			$compose --file docker-compose.local.yml logs -f --tail=20 epps-country-a epps-fmk-mock epps-cpr-mock
		;;
		stop|down)
			echo -e "\n\e[1;33m* Stopping and removing containers\e[0m\n"
			$compose --file docker-compose.local.yml down
		;;
		kill)
			echo -e "\n\e[1;33m* Stopping and removing containers\e[0m\n"
			$compose --file docker-compose.local.yml kill && $compose --file docker-compose.local.yml down
		;;
		startdb)
			echo -e "\n\e[1;33m* Starting database and pgweb\e[0m\n"
			$compose --file docker-compose.local.yml up -d mariadb-server
		;;
		startmocks)
			echo -e "\n\e[1;33m* Starting database and pgweb\e[0m\n"
			$compose --file docker-compose.local.yml up -d epps-fmk-mock epps-cpr-mock
		;;
		*)
			echo "Unknown arguments: $option"
			echo "Usage: $0 build|start|logs|stop|refresh"
			echo
			echo "See README.md for full documentation"
			exit 1
		;;
	esac

done
