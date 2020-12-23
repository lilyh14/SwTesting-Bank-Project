#!/bin/bash

if [ "$1" == "unit" ]
then
  sed -i -e '/REMOVABLE_START/,/REMOVABLE_END/{//!d;}' cloudbuild.yml
  sed -i -e '/REMOVABLE_START/r cloudbuild_unit.yml' cloudbuild.yml
fi

if [ "$1" == "integ" ]
then
  sed -i -e '/REMOVABLE_START/,/REMOVABLE_END/{//!d;}' cloudbuild.yml
  sed -i -e '/REMOVABLE_START/r cloudbuild_integ.yml' cloudbuild.yml
fi

if [ "$1" == "no_tests" ]
then
  sed -i -e '/REMOVABLE_START/,/REMOVABLE_END/{//!d;}' cloudbuild.yml
fi

if [ "$1" == "" ]
then
  sed -i -e '/REMOVABLE_START/,/REMOVABLE_END/{//!d;}' cloudbuild.yml
  sed -i -e '/REMOVABLE_START/r cloudbuild_unit.yml' cloudbuild.yml
  sed -i -e '/#___/r cloudbuild_integ.yml' cloudbuild.yml
fi

rm cloudbuild.yml-e