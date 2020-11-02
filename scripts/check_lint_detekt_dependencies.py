# coding=utf-8
import os
import requests


def check_detekt_version(currentversion):
    r = requests.get('https://api.github.com/repos/detekt/detekt/releases/latest')
    latest_version = r.json()['tag_name']
    if currentversion != latest_version:
        print("Detekt: " + currentversion + " -> " + latest_version)


def check_ktlint_version(currentversion):
    r = requests.get('https://api.github.com/repos/pinterest/ktlint/releases/latest')
    latest_version = r.json()['tag_name']
    if currentversion != latest_version:
        print("Ktlint: " + currentversion + " -> " + latest_version)


def check_detekt_or_ktlint(filename):
    if filename.startswith('detekt'):
        check_detekt_version(filename.split('_')[1])
    elif filename.startswith('ktlint'):
        check_ktlint_version(filename.split('_')[1])


for root, dirnames, filenames in os.walk("binary"):
    for filename in filenames:
        check_detekt_or_ktlint(filename)
