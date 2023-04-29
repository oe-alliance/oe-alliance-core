SUMMARY = "Plugins for Titan"
MAINTAINER = "TitanNit Team"
SECTION = "plugins"
#PRIORITY = "optional"
LICENSE = "GPLv2"
PACKAGE_ARCH = "${MACHINE_ARCH}"
PACKAGES_DYNAMIC = "titan-plugin-(?!oea-).*"

require conf/license/license-gplv2.inc

SRCREV = "${AUTOREV}"
PV = "${SRCPV}"

SRC_URI = "svn://buildbin:buildbin@sbnc.dyndns.tv;module=svn;protocol=http"

DEPENDS = "titan \
    ${PYTHON_PN}-pyopenssl \
    ${PYTHON_PN}-gdata-python3 \
    streamripper \
    ${PYTHON_PN}-mutagen \
    ${PYTHON_PN}-twisted \
    ${PYTHON_PN}-daap \
    ${PYTHON_PN}-google-api-python-client \
    ${PYTHON_PN}-httplib2 \
    ${PYTHON_PN}-youtube-dl \
    ${PYTHON_PN}-yt-dlp \
    ${PYTHON_PN}-six-native \
    libav \
    libshowiframe \
    libcddb \
    libtirpc \
    nmap \
    "

RDEPENDS:${PN} = "${PYTHON_PN}-ctypes"

S = "${WORKDIR}/svn/titan/plugins"

#inherit autotools-brokensep pkgconfig
#inherit autotools-brokensep
inherit autotools-brokensep gitpkgv python3native pkgconfig gettext

CFLAGS = "\
	-I${STAGING_DIR_TARGET}/usr/include \
	-I${STAGING_DIR_TARGET}/usr/include/curl \
	-I${STAGING_DIR_TARGET}/usr/include/python2.7 \
	-include Python.h \
    -I${STAGING_INCDIR}/${PYTHON_DIR} \
	-I${STAGING_DIR_TARGET}/usr/include/tirpc \
	-I${STAGING_DIR_TARGET}/usr/include \
	-I${STAGING_DIR_TARGET}/usr/include/freetype2 \
	-I${STAGING_DIR_TARGET}/usr/include/openssl \
	-I${STAGING_DIR_TARGET}/usr/include/dreamdvd \
	-I${STAGING_DIR_TARGET}/usr/include/libdreamdvd \
	-I${WORKDIR}/svn/titan/libdreamdvd \
	-I${WORKDIR}/svn/titan/titan \
	-I${WORKDIR}/svn/titan/titan/include \
	-I${WORKDIR}/svn/titan/libeplayer3/include"

CFLAGS:append:arm = "${@bb.utils.contains('GST_VERSION', '1.0', ' \
	-I${STAGING_DIR_TARGET}/usr/include \
	-I${STAGING_DIR_TARGET}/usr/lib/gstreamer-1.0/include \
	-I${STAGING_DIR_TARGET}/usr/include/gstreamer-1.0 \
	-I${STAGING_DIR_TARGET}/usr/include/glib-2.0 \
	-I${STAGING_DIR_TARGET}/usr/include/libxml2 \
	-I${STAGING_DIR_TARGET}/usr/lib/glib-2.0/include \
	-I${STAGING_DIR_TARGET}/usr/include/freetype2 \
	-I${STAGING_DIR_TARGET}/usr/include/dreamdvd \
	-I${STAGING_DIR_TARGET}/usr/include/libdreamdvd \	
	-I${WORKDIR}/titan/libdreamdvd \
	-I${WORKDIR}/titan/titan \
    ', ' \
	-I${STAGING_DIR_TARGET}/usr/include \
	-I${STAGING_DIR_TARGET}/usr/include/gstreamer-0.10 \
	-I${STAGING_DIR_TARGET}/usr/include/glib-2.0 \
	-I${STAGING_DIR_TARGET}/usr/include/libxml2 \
	-I${STAGING_DIR_TARGET}/usr/lib/glib-2.0/include \
	-I${STAGING_DIR_TARGET}/usr/include/freetype2 \
	-I${STAGING_DIR_TARGET}/usr/include/dreamdvd \
	-I${STAGING_DIR_TARGET}/usr/include/libdreamdvd \	
	-I${WORKDIR}/titan/libdreamdvd \
	-I${WORKDIR}/titan/titan \
', d)}"

CFLAGS:append:sh4 = " \
	-I${STAGING_DIR_TARGET}/usr/include/libmmeimage \
	-I${STAGING_KERNEL_DIR}/extra/bpamem \
	"

CFLAGS:append:sh4 = " -DOEBUILD -DEXTEPLAYER3 -DEPLAYER3 -DSH4 -DSH4NEW -DCAMSUPP -Os -export-dynamic -Wall -Wno-unused-but-set-variable -Wno-implicit-function-declaration"
CFLAGS:append:mipsel = " -DMIPSEL -DOEBUILD -DEXTEPLAYER3 -DEPLAYER3 -DCAMSUPP -Os -mhard-float -export-dynamic -Wall -Wno-unused-but-set-variable -Wno-implicit-function-declaration -Wno-unused-variable -Wno-format-overflow -Wno-format-truncation -Wno-nonnull -Wno-restrict"
CFLAGS:append:arm = " -DMIPSEL -DOEBUILD -DEXTGST -DEPLAYER4 -DEXTEPLAYER3 -DEPLAYER3 -DCAMSUPP -Os -mhard-float -export-dynamic -Wall -Wno-unused-but-set-variable -Wno-implicit-function-declaration -Wno-unused-variable -Wno-format-overflow -Wno-format-truncation -Wno-nonnull -Wno-restrict"

LDFLAGS:prepend = " -lcurl "

do_configure:prepend() {
    cd ${S}

    SVNVERSION=${SRCPV}
	echo SVNVERSION: ${SVNVERSION}

	sed "s/^#define PLUGINVERSION .*/#define PLUGINVERSION $SVNVERSION/" -i  ../titan/struct.h
	cat ../titan/struct.h | grep "define PLUGINVERSION"

}

EXTRA_OECONF = " \
    BUILD_SYS=${BUILD_SYS} \
    HOST_SYS=${HOST_SYS} \
    STAGING_INCDIR=${STAGING_INCDIR} \
    STAGING_LIBDIR=${STAGING_LIBDIR} \
"

FILES:${PN} = "/usr/local/share/titan/plugins"
INSANE_SKIP:${PN} = "already-stripped"

do_install() {
	if [ ${HOST_SYS} = "sh4-oe-linux" ];then
		HOST=sh4
	elif [ ${HOST_SYS} = "arm-oe-linux-gnueabi" ];then
		HOST=arm
	else
		HOST=mipsel
	fi
	
	install -d ${D}/usr/local/share/titan/plugins
	
	SECTIONLIST="`cat ../plugins/Makefile.am | sed 's/\\t\+/ /g' | sed 's/ \\+//g' | sed 's/\\\//g' | grep -v =`"
	echo SECTIONLIST $SECTIONLIST
	for SECTION in $SECTIONLIST;do
		echo SECTION $SECTION
    	PLUGINLIST="`cat ../plugins/$SECTION/Makefile.am | sed 's/\\t\+/ /g' | sed 's/ \\+//g' | sed 's/\\\//g' | grep -v =`"

	    for PLUGIN in $PLUGINLIST;do
		    echo PLUGIN $PLUGIN
		    install -d ${D}/usr/local/share/titan/plugins/$SECTION/$PLUGIN
		    install -m 0644 ../plugins/$SECTION/$PLUGIN/.libs/*.so ${D}/usr/local/share/titan/plugins/$SECTION/$PLUGIN/

echo SECTION $SECTION
echo PLUGIN $PLUGIN
echo HOST_SYS ${HOST_SYS}
echo HOST $HOST
	
		    if test -e ../plugins/$SECTION/$PLUGIN/$PLUGIN.sh;then
			    install -m 0655 ../plugins/$SECTION/$PLUGIN/*.sh ${D}/usr/local/share/titan/plugins/$SECTION/$PLUGIN
		    fi
		    if test -e ../plugins/$SECTION/$PLUGIN/$PLUGIN.conf;then
			    install -m 0655 ../plugins/$SECTION/$PLUGIN/*.conf ${D}/usr/local/share/titan/plugins/$SECTION/$PLUGIN
		    fi
		    if test -e ../plugins/$SECTION/$PLUGIN/files;then
			    cp -a ../plugins/$SECTION/$PLUGIN/files/* ${D}/usr/local/share/titan/plugins/$SECTION/$PLUGIN/
		    fi
		    if test -e ../plugins/$SECTION/$PLUGIN/$HOST;then
			    cp -a ../plugins/$SECTION/$PLUGIN/${HOST}/* ${D}/usr/local/share/titan/plugins/$SECTION/$PLUGIN/
		    fi
		    if test -e ../plugins/$SECTION/$PLUGIN/${HOST_SYS};then
			    cp -a ../plugins/$SECTION/$PLUGIN/f${HOST_SYS}/* ${D}/usr/local/share/titan/plugins/$SECTION/$PLUGIN/
		    fi
		    if test -e ../plugins/$SECTION/$PLUGIN/picons;then
			    cp -a ../plugins/$SECTION/$PLUGIN/picons ${D}/usr/local/share/titan/plugins/$SECTION/$PLUGIN/
		    fi
		    if test -e ../plugins/$SECTION/$PLUGIN/skin;then
			    cp -a ../plugins/$SECTION/$PLUGIN/skin ${D}/usr/local/share/titan/plugins/$SECTION/$PLUGIN/
		    fi
		    if test -e ../plugins/$SECTION/$PLUGIN/skin.xml;then
			    install -m 0644 ../plugins/$SECTION/$PLUGIN/skin.xml ${D}/usr/local/share/titan/plugins/$SECTION/$PLUGIN/
		    fi
		    if test -e ../plugins/$SECTION/$PLUGIN/plugin.png;then
			    install -m 0644 ../plugins/$SECTION/$PLUGIN/plugin.png ${D}/usr/local/share/titan/plugins/$SECTION/$PLUGIN/
		    fi
		    if test -e ../plugins/$SECTION/$PLUGIN/default.jpg;then
			    install -m 0644 ../plugins/$SECTION/$PLUGIN/default.jpg ${D}/usr/local/share/titan/plugins/$SECTION/$PLUGIN/
		    fi
	    done
	done
}

python populate_packages:prepend() {
    titan_plugindir = bb.data.expand('/usr/local/share/titan/plugins', d)
    do_split_packages(d, titan_plugindir, '(.*?/.*?)/.*', 'titan-plugin-%s', '%s', recursive=True, match_path=True, prepend=True, extra_depends="titan")

    def getControlLines(mydir, d, package):
        packagename = package[-1]

        import os
        try:
            print("package1 ", package)
            if(len(package) != 4):
                print("5 return")                
                return
            section = package[2]
            src = open(mydir + section + "/" + packagename + "/CONTROL/control").read()
        except IOError:
            return
        for line in src.split("\n"):
            rev = bb.data.expand('${SRCPV}', d)
            box = bb.data.expand('${MACHINE}', d)
            pr = bb.data.expand('${PR}', d)
            workdir = bb.data.expand('${WORKDIR}', d)

            full_package = package[0] + '-' + package[1] + '-' + package[2] + '-' + package[3]
            print("full_package ", full_package)

            filename = full_package + '_' + rev + '-' + pr + '_' + box
            print("filename ", filename)

            cmd = 'ls -al ' + mydir + section + '/' + packagename + '/preview/prev.png'
            print("cmd1 ", cmd)
            print(" ")
            os.system(cmd)

            cmd = 'mkdir -p ' + workdir + '/deploy-png/' + box
            print("cmd2 ", cmd)
            print(" ")
            os.system(cmd)

            cmd = 'cp -a ' + mydir + section + '/' + packagename + '/preview/prev.png ' + workdir + '/deploy-png/' + box + '/' + filename + '.png'
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

            postinstfile = mydir + section + '/' + packagename + "/CONTROL/postinst"
            postinst = open(postinstfile).read()
            print("postinst ", postinst)
            d.setVar('pkg_postinst:' + full_package, postinst)

            postrmfile = mydir + section + '/' + packagename + "/CONTROL/postrm"
            postrm = open(postrmfile).read()
            print("postrm ", postrm)
            d.setVar('pkg_postrm:' + full_package, postrm)

            preinstfile = mydir + section + '/' + packagename + "/CONTROL/preinst"
            preinst = open(preinstfile).read()
            print("preinst ", preinst)
            d.setVar('pkg_preinst:' + full_package, preinst)

            prermfile = mydir + section + '/' + packagename + "/CONTROL/prerm"
            prerm = open(prermfile).read()
            print("prerm ", prerm)
            d.setVar('pkg_prerm:' + full_package, prerm)

    mydir = d.getVar('D', True) + "/../svn/titan/plugins/"
    print("1mydir ", mydir)
    for package in d.getVar('PACKAGES', d, 1).split():
        getControlLines(mydir, d, package.split('-'))
}

do_package_qa() {
}

do_package_write_ipk:append() {
    bb.process.run("cp -a ../deploy-png/* .")
#    bb.process.run("cp -a ../preview .")
}


