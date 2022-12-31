GITHUB_ORGANIZATION ?= "opendreambox"
GITHUB_PROJECT ?= "${BPN}"
GITHUB_BRANCH ?= "master"
GITHUB_URI = "git://github.com/${GITHUB_ORGANIZATION}/${GITHUB_PROJECT}.git;protocol=https;branch=${GITHUB_BRANCH}"

SRC_URI += "${GITHUB_URI}"

inherit git-project opendreambox-srcrev
