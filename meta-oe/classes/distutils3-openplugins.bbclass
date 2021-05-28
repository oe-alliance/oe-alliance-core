inherit distutils3 gettext

# Scripts want to install "/etc", so we need "--root" instead of setting install-data stuff
# to remain compatible with previous versions.

DISTUTILS_INSTALL_ARGS = "\
    --root=${D} \
    --install-data=${datadir} \
    --install-lib=${libdir}/enigma2/python/Plugins \
    "

# Remove "egg-info" files. If datadir or site-packages dir is empty, remove it.
distutils3_do_install_append() {
	rm -f ${D}${libdir}/enigma2/python/Plugins/*.egg-info
	rmdir -p --ignore-fail-on-non-empty ${D}${datadir} ${D}/${PYTHON_SITEPACKAGES_DIR} || true
}
