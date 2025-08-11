inherit setuptools3_legacy gettext

# Scripts want to install "/etc", so we need "--root" instead of setting install-data stuff
# to remain compatible with previous versions.

SETUPTOOLS_INSTALL_ARGS = "\
    --root=${D} \
    --install-data=${datadir} \
    --install-lib=${libdir}/enigma2/python/Plugins \
    "

# Remove "egg-info" files. If datadir or site-packages dir is empty, remove it.
setuptools3_legacy_do_install:append() {
	rm -rf ${D}${libdir}/enigma2/python/Plugins/*.egg-info
	rmdir -p --ignore-fail-on-non-empty ${D}${datadir} ${D}/${PYTHON_SITEPACKAGES_DIR} || true
}

FILES:${PN} += "${libdir}/enigma2/python/Plugins"

# hack for missing locate for some old plugins
do_install:append() {
  found_locale=0
  for dir in ${S}/build/lib/Extensions/*/locale; do
    if [ -d "$dir" ]; then
      found_locale=1
      break
    fi
  done
  if [ "$found_locale" -eq 1 ]; then
    cp -rf ${S}/build/lib/* ${D}/usr/lib/enigma2/python/Plugins
  fi
}
