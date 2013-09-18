#!/bin/sh
cd $CI_HOME/example
sbt test
cd $CI_HOME/week01
sbt test
