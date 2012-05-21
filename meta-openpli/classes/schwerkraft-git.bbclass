SCHWERKRAFT_PROJECT ?= "${BPN}"

SRC_URI += "git://schwerkraft.elitedvb.net/${SCHWERKRAFT_PROJECT}/${SCHWERKRAFT_PROJECT}.git;protocol=git"

inherit git-project
