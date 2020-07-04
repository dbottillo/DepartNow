# coding=utf-8
from __future__ import print_function

import fnmatch
import os
import re
import shutil


class LogColor:
    ENDC = '\033[0m'
    BOLD = '\033[1m'
    UNDERLINE = '\033[4m'


def log(message, color=None, end='\n'):
    line = message if color is None else color + message + LogColor.ENDC
    print(line, end=end)


def perform_step(log_message, method):
    log(log_message, LogColor.BOLD, end=' ')
    try:
        method()
        log('✅')
    except:
        log('❌')
        raise


def copy_data_template():
    shutil.copytree('../template/template_data', feature_data_path)


def copy_feature_template():
    shutil.copytree('../template/template_ui', feature_path)


def create_feature_package():
    os.mkdir(feature_data_path + "src/main/java/com/dbottillo/replacename/feature")
    os.mkdir(feature_data_path + "src/test/java/com/dbottillo/replacename/feature")
    os.mkdir(feature_path + "src/main/java/com/dbottillo/replacename/feature")
    os.mkdir(feature_path + "src/test/java/com/dbottillo/replacename/feature")
    os.mkdir(feature_data_path + "src/main/java/com/dbottillo/replacename/feature/" + feature_name)
    os.mkdir(feature_data_path + "src/test/java/com/dbottillo/replacename/feature/" + feature_name)
    os.mkdir(feature_path + "src/main/java/com/dbottillo/replacename/feature/" + feature_name)
    os.mkdir(feature_path + "src/test/java/com/dbottillo/replacename/feature/" + feature_name)


def format_files_content():
    for root, dirnames, filenames in os.walk(feature_path):
        format_content(root, filenames)


def tidy_up_module():
    # To have them versioned in git, empty folders have an empty .gitignore file.
    # We don't really need them in our feature module so we can remove them.
    for root, dirnames, filenames in os.walk(feature_path):
        for filename in fnmatch.filter(filenames, '.gitkeep'):
            os.remove(os.path.join(root, filename))
    for root, dirnames, filenames in os.walk(feature_data_path):
        for filename in fnmatch.filter(filenames, '.gitkeep'):
            os.remove(os.path.join(root, filename))


def rename(root, filename, pattern, replacement):
    new_name = re.sub(pattern, replacement, filename)
    shutil.move(os.path.join(root, filename), os.path.join(root, *new_name.split("/")))


def filter_and_rename(root, names, placeholder, replaceValue):
    for name in fnmatch.filter(names, '*' + placeholder + '*'):
        rename(root, name, placeholder, replaceValue)


def format_content(root, filenames):
    for filename in filenames:
        if filename.endswith(('.xml', '.gradle')):
            fpath = os.path.join(root, filename)
            with open(fpath) as f:
                s = f.read()
            s = s.replace("{FEATURE_NAME}", feature_name)
            with open(fpath, "w") as f:
                f.write(s)


def integrate_feature():
    f = open('../settings.gradle', 'a')
    f.write('include \':feature_' + feature_name + ':' + feature_name + '_data\',\':feature_' + feature_name + ':' + feature_name + '_ui\' \n')
    f.close()


if __name__ == '__main__':
    # Move to script directory
    os.chdir(os.path.dirname(__file__))

    # Prompt for module name
    log('Feature name (e.g  login):', LogColor.BOLD, end=' ')
    feature_name = input()
    if not feature_name:
        raise ValueError('Invalid option: feature name can\'t be null')

    log('Creating ' + feature_name + ' feature')
    feature_data_path = '../feature_' + feature_name + '/' + feature_name + '_data/'
    feature_path = '../feature_' + feature_name + '/' + feature_name + '_ui/'
    perform_step('Copying data template...', copy_data_template)
    perform_step('Copying feature template...', copy_feature_template)
    perform_step('Create feature package', create_feature_package)
    perform_step('Formatting files content...', format_files_content)
    perform_step('Tidying up module...', tidy_up_module)
    perform_step('Adding ' + feature_name + ' to settings.gradle', integrate_feature)
