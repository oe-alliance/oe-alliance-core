SUMMARY = "3rd Party plugins for Enigma2"
MAINTAINER = "oe-alliance team"

LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://COPYING;md5=8e37f34d0e40d32ea2bc90ee812c9131"

inherit gitpkgv deploy

DEPENDS = "tslib mpfr gmp"

SRCREV = "${AUTOREV}"
PV = "${IMAGE_VERSION}+gitr${SRCPV}"
PKGV = "${IMAGE_VERSION}+gitr${GITPKGV}"
PR = "r20"

SRC_URI="git://github.com/oe-alliance/3rdparty-plugins-python3.git;branch=main;protocol=https"

EXTRA_OECONF = " \
    BUILD_SYS=${BUILD_SYS} \
    HOST_SYS=${HOST_SYS} \
    STAGING_INCDIR=${STAGING_INCDIR} \
    STAGING_LIBDIR=${STAGING_LIBDIR} \
"

ALLOW_EMPTY:${PN} = "1"
PACKAGE_ARCH = "${MACHINE_ARCH}"

S = "${WORKDIR}/git"


THIRDPARTY_PLUGINS = " \
    enigma2-plugin-extensions-ipchecker_004_all.ipk \
    enigma2-plugin-extensions-netspeedtest_2.1.4_all.ipk \
    enigma2-plugin-extensions-screengrabber_2.6_all.ipk \
    enigma2-plugin-extensions-skyrecorder_2.1.1d-py3_all.ipk \
    enigma2-plugin-extensions-sundtekcontrolcenter_20210624-2_all.ipk \
    enigma2-plugin-extensions-xcplugin-forever_2.2_all.ipk \
    "

# Install any packages that are only for this machines feed here
THIRDPARTY_MACHINE_PLUGINS:et8000 = " \
    enigma2-plugin-systemplugins-autorebootET8000_1.4_mipsel.ipk \
    "

python populate_packages:prepend () {
    pkg  = ""
    pkgs = ""
    plugins = d.getVar('THIRDPARTY_PLUGINS', True)
    if d.getVar('THIRDPARTY_MACHINE_PLUGINS', True) is not None:
        plugins += d.getVar('THIRDPARTY_MACHINE_PLUGINS', True)
    if d.getVar('THIRDPARTY_EXTRA_PLUGINS', True) is not None:
        plugins += d.getVar('THIRDPARTY_EXTRA_PLUGINS', True)

    if plugins is not None:
        for package in plugins.split():
            pkg = package.split('_')[0]
            pkgs += pkg + " "
            d.setVar('ALLOW_EMPTY:' + pkg, '1')

    d.setVar('PACKAGES', pkgs)
}

do_deploy() {
    rm -rf ${DEPLOY_DIR_IPK}/3rdparty
    rm -rf ${DEPLOY_DIR_IPK}/${MACHINE}_3rdparty
    install -d 0755 ${DEPLOY_DIR_IPK}/3rdparty
    install -d 0755 ${DEPLOY_DIR_IPK}/${MACHINE}_3rdparty
    for i in ${THIRDPARTY_PLUGINS}; do
        if [ -f $i ]; then
            install -m 0644 $i ${DEPLOY_DIR_IPK}/3rdparty;
        fi
    done;
    for i in ${THIRDPARTY_MACHINE_PLUGINS}; do
        if [ -f $i ]; then
            install -m 0644 $i ${DEPLOY_DIR_IPK}/${MACHINE}_3rdparty;
        fi
    done;
    for i in ${THIRDPARTY_EXTRA_PLUGINS}; do
        if [ -f $i ]; then
            install -m 0644 $i ${DEPLOY_DIR_IPK}/3rdparty;
        fi
    done;
    pkgdir=${DEPLOY_DIR_IPK}/3rdparty
    if [ -e $pkgdir ]; then
        chmod 0755 $pkgdir
    fi
    pkgdir=${DEPLOY_DIR_IPK}/${MACHINE}_3rdparty
    if [ -e $pkgdir ]; then
        chmod 0755 $pkgdir
    fi
}

do_package_write_ipk() {
    :
}

addtask do_deploy before do_package_write after do_package_write_ipk
