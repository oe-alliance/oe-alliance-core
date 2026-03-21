# OE-Alliance Minimal Image - bootable Linux with shell
# Includes only core Linux system and machine platform support

IMAGE_INSTALL = "\
    packagegroup-oea-linux-core \
    packagegroup-oea-machine-core \
    "

IMAGE_FEATURES += "package-management allow-empty-password empty-root-password allow-root-login"

INHIBIT_DEFAULT_DEPS = "1"
