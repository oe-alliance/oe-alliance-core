inherit distutils

# Scripts want to install "/etc", so we need "--root" instead of setting install-data stuff
# to remain compatible with previous versions.

DISTUTILS_INSTALL_ARGS = "\
    --root=${D} \
    --install-data=${datadir} \
    --install-lib=/usr/lib/enigma2/python/Plugins \
    "
