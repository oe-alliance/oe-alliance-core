# OE-Alliance Headless Image - server/headless STB
# Includes Linux core, networking, and Python support without GUI

IMAGE_INSTALL = "\
    packagegroup-oea-linux-core \
    packagegroup-oea-machine-core \
    packagegroup-oea-network-core \
    packagegroup-oea-python-core \
    "

IMAGE_FEATURES += "package-management allow-empty-password empty-root-password allow-root-login"

INHIBIT_DEFAULT_DEPS = "1"
