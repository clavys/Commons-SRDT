#!/bin/bash

SLUG="atlanmod/Commons"
JDK="oraclejdk8"
BRANCH="master"
OS="linux"

if [ "${TRAVIS_REPO_SLUG}" != "${SLUG}" ]; then
  echo "Skipping Javadoc publication: wrong repository. Expected '${SLUG}' but was '${TRAVIS_REPO_SLUG}'."
elif [ "${TRAVIS_JDK_VERSION}" != "${JDK}" ]; then
  echo "Skipping Javadoc publication: wrong JDK. Expected '${JDK}' but was '${TRAVIS_JDK_VERSION}'."
elif [ "${TRAVIS_PULL_REQUEST}" != "false" ]; then
  echo "Skipping Javadoc publication: was pull request."
elif [ "${TRAVIS_BRANCH}" != "${BRANCH}" ]; then
  echo "Skipping Javadoc publication: wrong branch. Expected '${BRANCH}' but was '${TRAVIS_BRANCH}'."
elif [ "${TRAVIS_OS_NAME}" != "${OS}" ]; then
  echo "Skipping Javadoc publication: wrong OS. Expected '${OS}' but was '${TRAVIS_OS_NAME}'."
else
    echo -e "Generating Javadoc..."

    mvn -B -q javadoc:javadoc javadoc:aggregate -P "deploy-javadoc" &> /dev/null

    if ! [ -d target/site/apidocs ]; then
        echo -e "Skipping Javadoc publication: no Javadoc has been generated."
        exit
    fi

    echo -e "Copying Javadoc..."

    cp -Rfv target/site/apidocs/ ~/doc/

    if ! [ -d ~/gh-pages ]; then
        echo -e "Cloning 'gh-pages' branch..."

        git config --global user.email "travis@travis-ci.org"
        git config --global user.name "travis-ci"

        mkdir ~/gh-pages
        git -C ~/ clone --branch=gh-pages https://${GH_TOKEN}@github.com/${TRAVIS_REPO_SLUG} gh-pages
    fi

    echo -e "Merging Javadoc..."

    mkdir -p --verbose ~/gh-pages/releases/snapshot

    if [ -d ~/gh-pages/releases/snapshot/doc ]; then
        echo -e "Cleaning existing artifacts..."

        git rm -rf ~/gh-pages/releases/snapshot/doc/*
    fi

    cp -Rfv ~/doc/ ~/gh-pages/releases/snapshot/doc/

    git add -Af

    echo -e "Checking for differences..."

    if [ -z "$(git -C ~/gh-pages status --porcelain)" ]; then
        echo -e "Skipping Javadoc publication: no change."
        exit
    fi

    echo -e "Publishing Javadoc..."

    git -C ~/gh-pages commit --quiet -m "[auto] update the Javadoc from Travis #${TRAVIS_BUILD_NUMBER}"
    git -C ~/gh-pages push --quiet -f origin gh-pages

    echo -e "Javadoc published."
fi
