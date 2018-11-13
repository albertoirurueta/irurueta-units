#!/usr/bin/env bash
if [ "$TRAVIS_BRANCH" = 'master' ] && [ "$TRAVIS_PULL_REQUEST" == 'false' ]; then
    openssl aes-256-cbc -K $encrypted_4916c94cad27_key -iv $encrypted_4916c94cad27_iv -in cd/codesigning.asc.enc -out cd/codesigning.asc -d
    gpg --fast-import cd/codesigning.asc
fi
