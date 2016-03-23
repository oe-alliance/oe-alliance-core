SUMMARY = "3rd Party plugins for Enigma2"
MAINTAINER = "OpenNFR Team"

LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=45de10587e108efb50c321c1affd5e00"

inherit gitpkgv autotools deploy

SRCREV = "${AUTOREV}"
PV = "git${SRCPV}"
PKGV = "git${GITPKGV}"
PR = "r38"
SRC_URI="git://github.com/carlo0815/3rdparty-plugins.git;protocol=git;branch=4.4"

EXTRA_OECONF = " \
    BUILD_SYS=${BUILD_SYS} \
    HOST_SYS=${HOST_SYS} \
    STAGING_INCDIR=${STAGING_INCDIR} \
    STAGING_LIBDIR=${STAGING_LIBDIR} \
    --with-boxtype=${MACHINEBUILD} \
"

ALLOW_EMPTY_${PN} = "1"
PACKAGE_ARCH = "${MACHINE_ARCH}"

S = "${WORKDIR}/git"

DEPENDS = "enigma2"

do_install() {
}

do_deploy() {
}

do_deploy_append() {    
    install -d 0755 ${DEPLOY_DIR_IPK}/3rdparty
    install -d 0755 ${DEPLOY_DIR_IPK}/${MACHINE}_3rdparty
    if [ "${TARGET_ARCH}" = "mipsel" ]; then
    	 install -m 0644 ${S}/*all.ipk ${DEPLOY_DIR_IPK}/3rdparty #|| true
   	 install -m 0644 ${S}/*mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty #|| true
   	 install -m 0644 ${S}/*mips32el.ipk ${DEPLOY_DIR_IPK}/3rdparty #|| true
    fi
    if [ "${TARGET_ARCH}" = "sh4" ]; then
    	 install -m 0644 ${S}/*all.ipk ${DEPLOY_DIR_IPK}/3rdparty #|| true
    	 install -m 0644 ${S}/*sh4.ipk ${DEPLOY_DIR_IPK}/3rdparty #|| true
    fi
    if [ "${TARGET_ARCH}" = "arm" ]; then
    	 install -m 0644 ${S}/*all.ipk ${DEPLOY_DIR_IPK}/3rdparty #|| true
   	     install -m 0644 ${S}/*mipsel.ipk ${DEPLOY_DIR_IPK}/3rdparty #|| true
   	     install -m 0644 ${S}/*mips32el.ipk ${DEPLOY_DIR_IPK}/3rdparty #|| true
   	fi
    Z1=$(ls -1 ${S}/*${MACHINE}.ipk | wc -l)
    if [ $Z1 -gt 0 ]; then
        install -m 0644 ${S}/*${MACHINE}.ipk ${DEPLOY_DIR_IPK}/${MACHINE} #|| true
    fi 
    pkgdir=${DEPLOY_DIR_IPK}/3rdparty
    if [ -e $pkgdir ]; then
        chmod 0755 $pkgdir
    fi
    pkgdir=${DEPLOY_DIR_IPK}/${MACHINE}_3rdparty
    if [ -e $pkgdir ]; then
        chmod 0755 $pkgdir
    fi
}

addtask do_deploy before do_package_write after do_package_write_ipk 
