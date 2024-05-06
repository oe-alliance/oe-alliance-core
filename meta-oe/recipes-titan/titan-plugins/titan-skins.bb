SUMMARY = "Skins for Titan"
MAINTAINER = "TitanNit Team"
SECTION = "skins"
#PRIORITY = "optional"
LICENSE = "GPLv2"
PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

SRCREV = "${AUTOREV}"
PV = "${@bb.fetch2.get_srcrev(d)}"

SRC_URI = "svn://buildbin:buildbin@sbnc.dyndns.tv/svn/titan;module=skins;protocol=http"

S = "${WORKDIR}/skins"

FILES:${PN} = "/usr/local/share/titan/skin"

do_install() {
    cd ${S}

	install -d ${D}/usr/local/share/titan/skin
	LIST="$(ls -1)"
	echo LIST2 $LIST

	for ROUND in $LIST;do
		echo ROUND $ROUND
		install -d ${D}/usr/local/share/titan/skin/$ROUND
        cp -a $ROUND/* ${D}/usr/local/share/titan/skin/$ROUND
	done
}

python populate_packages:prepend() {
    titan_skindir = bb.data.expand('/usr/local/share/titan/skin', d)
    do_split_packages(d, titan_skindir, '(.*?)/.*', 'titan-plugin-skins-%s', 'Titan Skin: %s', recursive=True, match_path=True, prepend=True, extra_depends="")
    def getControlLines(mydir, d, package):
        packagename = package[-1]

        import os
        try:
            print("package1 ", package)
            if(len(package) != 4):
                print("5 return")                
                return
            section = package[2]
            src = open(mydir + "/" + packagename + "/CONTROL/control").read()
        except IOError:
            return
        for line in src.split("\n"):
            rev = bb.data.expand('${PV}', d)
            box = bb.data.expand('${MACHINE}', d)
            pr = bb.data.expand('${PR}', d)
            workdir = bb.data.expand('${WORKDIR}', d)

            full_package = package[0] + '-' + package[1] + '-' + package[2] + '-' + package[3]
            print("full_package ", full_package)

            filename = full_package + '_' + rev + '-' + pr + '_' + box
            print("filename ", filename)

            cmd = 'ls -al ' + mydir + '/' + packagename + '/prev.png'
            print("cmd1 ", cmd)
            print(" ")
            os.system(cmd)

            cmd = 'mkdir -p ' + workdir + '/deploy-png/' + box
            print("cmd2 ", cmd)
            print(" ")
            os.system(cmd)

            cmd = 'cp -a ' + mydir + '/' + packagename + '/prev.png ' + workdir + '/deploy-png/' + box + '/' + filename + '.png'
            print("cmd3 ", cmd)
            print(" ")
            os.system(cmd)

            if line.startswith('Description: '):
                print("found decription ", line[13:])
                d.setVar('DESCRIPTION:' + full_package, line[13:])
                d.setVar('SUMMARY:' + full_package, line[13:])
            elif line.startswith('Showname: '):
                print("found showname ", line[10:])
                cmd = 'echo "' + line[10:] + '" > ' + workdir + '/deploy-png/' + box + '/' + filename + '.showname'
                print("cmd4 ", cmd)
                print(" ")
                os.system(cmd)
                d.setVar('MAINTAINER:' + full_package, line[10:])
            elif line.startswith('Usepath: '):
                print("found Usepath ", line[9:])
                cmd = 'echo "' + line[9:] + '" > ' + workdir + '/deploy-png/' + box + '/' + filename + '.usepath'
                print("cmd5 ", cmd)
                print(" ")
                os.system(cmd)
            elif line.startswith('Homepage: '):
                d.setVar('HOMEPAGE:' + full_package, line[10:])
            elif line.startswith('Maintainer: '):
                d.setVar('MAINTAINER:' + full_package, line[12:])

            postinstfile = mydir + '/' + packagename + "/CONTROL/postinst"
            postinst = open(postinstfile).read()
            print("postinst ", postinst)
            d.setVar('pkg_postinst:' + full_package, postinst)

            postrmfile = mydir + '/' + packagename + "/CONTROL/postrm"
            postrm = open(postrmfile).read()
            print("postrm ", postrm)
            d.setVar('pkg_postrm:' + full_package, postrm)

            preinstfile = mydir + '/' + packagename + "/CONTROL/preinst"
            preinst = open(preinstfile).read()
            print("preinst ", preinst)
            d.setVar('pkg_preinst:' + full_package, preinst)

            prermfile = mydir + '/' + packagename + "/CONTROL/prerm"
            prerm = open(prermfile).read()
            print("prerm ", prerm)
            d.setVar('pkg_prerm:' + full_package, prerm)

    mydir = d.getVar('D', True) + "/../skins/"
    print("1mydir ", mydir)
    for package in d.getVar('PACKAGES', d, 1).split():
        getControlLines(mydir, d, package.split('-'))
}

do_package_qa() {
}

do_package_write_ipk:append() {
    bb.process.run("cp -a ../deploy-png/* .")
}

PACKAGES_DYNAMIC = "titan-plugin-skins-*"
