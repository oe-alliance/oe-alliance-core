# OE-Alliance Enigma2 Image - full STB with Enigma2 (without distro-specific skin)
# Base for all distro images - distros add their own packagegroup on top

IMAGE_INSTALL = "\
    packagegroup-oea-linux-core \
    packagegroup-oea-machine-core \
    packagegroup-oea-network-core \
    packagegroup-oea-python-core \
    packagegroup-oea-multimedia \
    packagegroup-oea-gui \
    packagegroup-oea-enigma2-core \
    "

IMAGE_FEATURES += "package-management allow-empty-password empty-root-password allow-root-login"

INHIBIT_DEFAULT_DEPS = "1"
