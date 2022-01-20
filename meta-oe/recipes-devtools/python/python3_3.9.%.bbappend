inherit python3-dir

FILES:${PN}-src += " \
    ${libdir}/${PYTHON_DIR}/*.py \
    ${libdir}/${PYTHON_DIR}/*/*.py \
    ${libdir}/${PYTHON_DIR}/*/*/*.py \
    ${libdir}/${PYTHON_DIR}/*/*/*/*.py \
    ${libdir}/${PYTHON_DIR}/*/*/*/*/*.py \
    ${libdir}/${PYTHON_DIR}/*/*/*/*/*/*.py \
    "

CFLAGS:append:sh4 = " -std=gnu11"

# hide compile warnings
do_install:append() {
    if [ -e ${D}/usr/lib/${PYTHON_DIR}/test ]; then
      rm -rf ${D}/usr/lib/${PYTHON_DIR}/test
    fi
    if [ -e ${D}/usr/lib/${PYTHON_DIR}/lib2to3/tests ]; then
      rm -rf ${D}/usr/lib/${PYTHON_DIR}/lib2to3/tests
    fi
}

fakeroot do_compileall() {
    python3 -m compileall -b "${D}"
}

do_compileall[depends] += "virtual/fakeroot-native:do_populate_sysroot"

addtask compileall before do_package after do_install

python(){
    import collections, json

    filename = os.path.join(d.getVar('THISDIR'), 'python3', 'python3-manifest.json')
    # This python changes the datastore based on the contents of a file, so mark
    # that dependency.
    bb.parse.mark_dependency(d, filename)

    with open(filename) as manifest_file:
        manifest_str =  manifest_file.read()
        json_start = manifest_str.find('# EOC') + 6
        manifest_file.seek(json_start)
        manifest_str = manifest_file.read()
        python_manifest = json.loads(manifest_str, object_pairs_hook=collections.OrderedDict)

    # First set RPROVIDES for -native case
    # Hardcoded since it cant be python3-native-foo, should be python3-foo-native
    pn = 'python3'
    rprovides = (d.getVar('RPROVIDES') or "").split()

    # ${PN}-misc-native is not in the manifest
    rprovides.append(pn + '-misc-native')

    for key in python_manifest:
        pypackage = pn + '-' + key + '-native'
        if pypackage not in rprovides:
              rprovides.append(pypackage)

    d.setVar('RPROVIDES:class-native', ' '.join(rprovides))

    # Then work on the target
    include_pycs = d.getVar('INCLUDE_PYCS')

    packages = d.getVar('PACKAGES').split()
    pn = d.getVar('PN')

    newpackages=[]
    for key in python_manifest:
        pypackage = pn + '-' + key

        if pypackage not in packages:
            # We need to prepend, otherwise python-misc gets everything
            # so we use a new variable
            newpackages.append(pypackage)

        # "Build" python's manifest FILES, RDEPENDS and SUMMARY
        d.setVar('FILES:' + pypackage, '')
        for value in python_manifest[key]['files']:
            d.appendVar('FILES:' + pypackage, ' ' + value)

        # Add cached files
        if include_pycs == '1':
             d.appendVar('FILES:' + pypackage, ' ' + value + 'c')

        for value in python_manifest[key]['rdepends']:
            # Make it work with or without $PN
            if '${PN}' in value:
                value=value.split('-', 1)[1]
            d.appendVar('RDEPENDS:' + pypackage, ' ' + pn + '-' + value)

        for value in python_manifest[key].get('rrecommends', ()):
            if '${PN}' in value:
                value=value.split('-', 1)[1]
            d.appendVar('RRECOMMENDS:' + pypackage, ' ' + pn + '-' + value)

        d.setVar('SUMMARY:' + pypackage, python_manifest[key]['summary'])

    # Prepending so to avoid python-misc getting everything
    packages = newpackages + packages
    d.setVar('PACKAGES', ' '.join(packages))
    d.setVar('ALLOW_EMPTY:${PN}-modules', '1')
    d.setVar('ALLOW_EMPTY:${PN}-pkgutil', '1')
}

