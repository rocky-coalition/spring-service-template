#!/bin/bash
set -eo pipefail

export PATH=~/go/bin/:~/Library/Python/2.7/bin/:$PATH

function get_pip() {
	curl https://bootstrap.pypa.io/get-pip.py -o get-pip.py
	python get-pip.py
	rm get-pip.py
}

if ! [ -x \"$(command -v awslocal)\" ]; then
	if ! [ -x \"$(command -v pip)\" ]; then
		get_pip
	fi
	pip install awscli-local
fi

awslocal secretsmanager put-secret-value --secret-id /local/distribution/spring-service-template --secret-string '{"database.username":"localuser", "database.password":"Pa55w.rd", "database.host":"localhost", "database.name":"sample_db", "database.port":"5432"}'
awslocal secretsmanager put-secret-value --secret-id /local-docker/distribution/spring-service-template --secret-string '{"database.username":"localuser", "database.password":"Pa55w.rd", "database.host":"localstack", "database.name":"sample_db", "database.port":"5432"}'

awslocal sns create-topic --name local-events-legacy-raw
awslocal sns create-topic --name local-docker-events-legacy-raw
