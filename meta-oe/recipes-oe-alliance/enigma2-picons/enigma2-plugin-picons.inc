SUMMARY = "enigma2 picons"
inherit allarch

require conf/license/license-gplv2.inc

DEPENDS = "pngcrush-native"

PACKAGES_DYNAMIC = "enigma2-plugin-picons-*"

S = "${WORKDIR}/picons"

do_install() {
    install -d ${D}/picon
}

RESIZE_COMMAND = ""

python populate_packages_prepend() {
    import os
    import shutil
    import re
    import tempfile
    dir = d.getVar('D', True)
    sdir = d.getVar('S', True)
    satnames = d.getVar('PICON_SATNAMES', True)
    pnsuffix = d.getVar('PN_SUFFIX', True)
    resize = d.getVar('RESIZE_COMMAND', True)
    if not pnsuffix:
        pnsuffix = ''
    piconpackages = []
    tmpdir = tempfile.mkdtemp()
    tmpfile = os.path.join(tmpdir, 'tmp.png')
    for sat in os.listdir(sdir):
        def createPackageName(type, sat):
            if sat[-3] == '.':
                zeros = '000'
                pos = zeros[:6 - len(sat)] + sat[:-1]
                if sat[-1] == 'e':
                    return "enigma2-plugin-picons-" + type + "-east-" + pos
                elif sat[-1] == 'w':
                    return "enigma2-plugin-picons-" + type + "-west-" + pos
            return "enigma2-plugin-picons-" + type + "-" + sat + pnsuffix
        if sat == "patches":
            continue
        if sat not in satnames:
            continue
        packagename = createPackageName('tv', sat)
        files = []
        srcdir = os.path.join(sdir, sat, 'tv')
        path = os.path.join(dir, 'picon')
        for file in os.listdir(srcdir):
            if file.endswith(".png"):
                fn = os.path.join(srcdir, file)
                dn = os.path.join(path, file)
                if os.path.islink(fn):
                    os.rename(fn, dn)
                else:
                    if resize:
                        os.system(resize % (fn, tmpfile))
                        fn = tmpfile
                    os.system("pngcrush -q -reduce '%s' '%s'" % (fn, dn))
                files.append("/picon/" + file)

        d.setVar('FILES_' + packagename, " ".join(files))
        d.setVar('DESCRIPTION_' + packagename, "TV picon set")
        piconpackages.append(packagename)
        try:
            packagename = createPackageName('radio', sat)
            files = []
            for file in os.listdir(sdir + "/" + sat + "/radio"):
                if file.endswith(".png"):
                    os.system("cp -a \"" + sdir + "/" + sat + "/radio/" + file + "\" " + dir + "/picon/")
                    files.append("/picon/" + file)
            d.setVar('FILES_' + packagename, " ".join(files))
            d.setVar('DESCRIPTION_' + packagename, "Radio picon set")
            piconpackages.append(packagename)
        except:
            pass
    try:
        os.unlink(tmpfile)
    except:
        pass
    try:
        os.rmdir(tmpdir)
    except:
        pass
    d.setVar('PACKAGES', ' '.join(piconpackages))
    d.setVar('PKGD', dir)
}

do_prepare_recipe_sysroot[noexec] = "1"
do_populate_sysroot[noexec] = "1"
do_package_qa[noexec] = "1"
