#!/bin/bash
set -ex
wget -O ~/FirefoxSetup.tar.bz2 "https://download.mozilla.org/?product=firefox-latest&os=linux64"
sudo tar xjf ~/FirefoxSetup.tar.bz2 -C /opt/
sudo mv /usr/lib/firefox/firefox /usr/lib/firefox/firefox_backup
sudo ln -s /opt/firefox/firefox /usr/lib/firefox/firefox