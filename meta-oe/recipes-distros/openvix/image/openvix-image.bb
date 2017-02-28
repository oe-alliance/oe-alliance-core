SUMMARY = "OpenViX Image"
MAINTAINER = "OpenViX"
SECTION = "base"
PRIORITY = "required"
LICENSE = "proprietary"
PACKAGE_ARCH = "${MACHINE_ARCH}"

require conf/license/license-gplv2.inc

PR_NUM = "${@bb.utils.contains("DISTRO_TYPE", "release", "${BUILD_VERSION}.000", "${BUILD_VERSION}.${DEVELOPER_BUILD_VERSION}", d)}"

PV = "${IMAGE_VERSION}"
PR = "r${PR_NUM}"

IMAGE_INSTALL = "openvix-base"

export IMAGE_BASENAME = "openvix-image"
IMAGE_LINGUAS = ""

IMAGE_FEATURES += "package-management"

inherit image

do_package_remove_unused_ipk () {
    set -x

    ipkgarchs="${ALL_MULTILIB_PACKAGE_ARCHS} ${SDK_PACKAGE_ARCHS}"
    unused="*-dbg_* *-dev_* *-staticdev_* *-doc_* *-demos_* *-examples_* *-sourcecode_* *-locale-* *-localedata-*"

    if [ ! -z "${DEPLOY_KEEP_PACKAGES}" ]; then
        return
    fi

    packagedirs="${DEPLOY_DIR_IPK}"
    for arch in $ipkgarchs; do
        packagedirs="$packagedirs ${DEPLOY_DIR_IPK}/$arch"
    done

    multilib_archs="${MULTILIB_ARCHS}"
    for arch in $multilib_archs; do
        packagedirs="$packagedirs ${DEPLOY_DIR_IPK}/$arch"
    done

    for pkgdir in $packagedirs; do
        if [ -e $pkgdir/ ]; then
            for i in ${unused}; do
                rm -f $pkgdir/$i;
            done;
        fi
    done
}
# addtask package_remove_unused_ipk before do_rootfs
