#!/bin/sh
cd $CI_HOME/example
sbt ++$TRAVIS_SCALA_VERSION test
