OPENDREAMBOX_PROJECT ?= "${BPN}"

SRC_URI += "git://github.com/oe-mirrors/${OPENDREAMBOX_PROJECT}.git;protocol=https;branch=master"

inherit git-project
