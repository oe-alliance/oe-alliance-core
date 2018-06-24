FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"


SRC_URI += " \
            file://04-default-is-optimized.patch \
            file://99-ignore-optimization-flag.patch \
            file://no-ldconfig.patch \
            file://setuptweaks-2.patch \
            file://pgettext.patch \
            file://create_unverified_context.patch \
            file://random.patch \
"

EXTRA_OECONF += " \
    ac_cv_file__dev_ptmx=yes \
    ac_cv_file__dev_ptc=no \
    ac_cv_no_strict_aliasing_ok=yes \
    ac_cv_pthread=yes \
    ac_cv_cxx_thread=yes \
    ac_cv_sizeof_off_t=8 \
"

FILES_${PN}-src += "${libdir}/python${PYTHON_MAJMIN}/*.py"
FILES_${PN}-src += "${libdir}/python${PYTHON_MAJMIN}/*/*.py"
FILES_${PN}-src += "${libdir}/python${PYTHON_MAJMIN}/*/*/*.py"
FILES_${PN}-src += "${libdir}/python${PYTHON_MAJMIN}/*.exe"
FILES_${PN}-src += "${libdir}/python${PYTHON_MAJMIN}/*/*.exe"
FILES_${PN}-src += "${libdir}/python${PYTHON_MAJMIN}/*/*/*.exe"
FILES_${PN}-src += "${libdir}/python${PYTHON_MAJMIN}/*.whl"
FILES_${PN}-src += "${libdir}/python${PYTHON_MAJMIN}/*/*.whl"
FILES_${PN}-src += "${libdir}/python${PYTHON_MAJMIN}/*/*/*.whl"
FILES_${PN}-src += "${libdir}/python${PYTHON_MAJMIN}/config/*"

FILES_${PN}-sqlite3-tests += "${libdir}/python${PYTHON_MAJMIN}/*/test* ${libdir}/python${PYTHON_MAJMIN}/*/*/test*"

FILES_${PN}-tests += " \
    ${libdir}/python${PYTHON_MAJMIN}/*/tests/* \
    ${libdir}/python${PYTHON_MAJMIN}/*/*/tests/* \
    ${libdir}/python${PYTHON_MAJMIN}/*/test/* \
    ${libdir}/python${PYTHON_MAJMIN}/*/*/test/* \
"

python(){
    import json

    filename = os.path.join(d.getVar('THISDIR'), 'python', 'python2-manifest.json')
    # This python changes the datastore based on the contents of a file, so mark
    # that dependency.
    bb.parse.mark_dependency(d, filename)

    with open(filename) as manifest_file:
        python_manifest=json.load(manifest_file)

    packages = d.getVar('PACKAGES').split()
    pn = d.getVar('PN')

    newpackages=[]
    newpackages.append('python-src')

    for key in python_manifest:
        pypackage= pn + '-' + key

        if pypackage not in packages:
            # We need to prepend, otherwise python-misc gets everything
            # so we use a new variable
            newpackages.append(pypackage)

        # "Build" python's manifest FILES, RDEPENDS and SUMMARY
        d.setVar('FILES_' + pypackage, '')
        for value in python_manifest[key]['files']:
           if value.endswith('.py'):
               d.appendVar('FILES_' + pypackage, ' ' + value + 'o')
               d.appendVar('FILES_python-src', ' ' + value)
           else:
               d.appendVar('FILES_' + pypackage, ' ' + value)

        d.setVar('RDEPENDS_' + pypackage, '')
        for value in python_manifest[key]['rdepends']:
            # Make it work with or without $PN
            if '${PN}' in value:
                value=value.split('-')[1]
            d.appendVar('RDEPENDS_' + pypackage, ' ' + pn + '-' + value)
        d.setVar('SUMMARY_' + pypackage, python_manifest[key]['summary'])

    # We need to ensure staticdev packages match for files first so we sort in reverse
    newpackages.sort(reverse=True)
    # Prepending so to avoid python-misc getting everything
    packages = newpackages + packages
    d.setVar('PACKAGES', ' '.join(packages))
    d.setVar('ALLOW_EMPTY_${PN}-modules', '1')
}
