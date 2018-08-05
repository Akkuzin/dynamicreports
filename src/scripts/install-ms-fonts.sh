#!/usr/bin/env bash

# accept EULA
echo ttf-mscorefonts-installer msttcorefonts/accepted-mscorefonts-eula select true | sudo debconf-set-selections

# install font
sudo apt-get install ttf-mscorefonts-installer