SUMMARY = "Picons for Titan"
MAINTAINER = "TitanNit Team"
SECTION = "picons"
#PRIORITY = "optional"
LICENSE = "GPLv2"
PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

SRCREV = "${AUTOREV}"
PV = "${@bb.fetch2.get_srcrev(d)}"

SRC_URI = "svn://public:public@sbnc.dyndns.tv/svn/ipk/source;module=picons_gold_SAT19_2;protocol=http"

S = "${WORKDIR}/picons_gold_SAT19_2"

FILES:${PN} = "/usr/local/share/titan/picons"

do_install() {
    cd ${S}
    install -d ${D}/usr/local/share/titan
    cp -a _path_/usr/local/share/titan/picons ${D}/usr/local/share/titan

}

python populate_packages:prepend() {
    def getControlLines(mydir, d, package):
        packagename = package[-1]

        import os

        try:
            print("package1 ", package)
            if(len(package) != 4):
                print("5 return")                
                return
            section = package[2]

            path = mydir + "/CONTROL/control"
            print("path ", path)

            src = open(path).read()
            print("src ", src)
        except IOError:
            return

        for line in src.split("\n"):
            rev = bb.data.expand('${PV}', d)
            box = bb.data.expand('${MACHINE}', d)
            pr = bb.data.expand('${PR}', d)
            workdir = bb.data.expand('${WORKDIR}', d)

            full_package = package[0] + '-' + package[1] + '-' + package[2] + '-' + package[3]
            print("full_package ", full_package)

            pic = package[0] + '-' + package[1] + '-' + package[2] + '-' + package[3] + '_' + rev + '-' + pr + '_' + box + '.png'
            print("pic ", pic)

            cmd = 'ls -al ' + mydir + '/preview/prev.png'
            print("cmd1 ", cmd)
            print(" ")
            os.system(cmd)

            cmd = 'mkdir -p ' + workdir + '/deploy-png/' + box + '/preview/'
            print("cmd2 ", cmd)
            print(" ")
            os.system(cmd)

            cmd = 'cp -a ' + mydir + '/preview/prev.png ' + workdir + '/deploy-png/' + box + '/' + pic
            print("cmd3 ", cmd)
            print(" ")
            os.system(cmd)

            if line.startswith('Description: '):
                print("found decription ", line[13:])
                d.setVar('DESCRIPTION:' + full_package, line[13:])
                d.setVar('SUMMARY:' + full_package, line[13:])
            elif line.startswith('Showname: '):
                print("found showname ", line[10:])
                d.setVar('SHOWNAME_' + full_package, line[10:])
            elif line.startswith('Maintainer: '):
                d.setVar('MAINTAINER:' + full_package, line[12:])

    mydir = bb.data.expand('${S}', d)
    print("mydir ", mydir)

    for package in d.getVar('PACKAGES', d, 1).split():
        getControlLines(mydir, d, package.split('-'))
}

do_package_qa() {
}

do_package_write_ipk:append() {
    bb.process.run("cp -a ../deploy-png/* .")
}
