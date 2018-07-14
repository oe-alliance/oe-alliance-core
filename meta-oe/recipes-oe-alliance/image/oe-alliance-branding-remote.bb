DESCRIPTION = "Additional remote control visuals for the OE-A Branding Lib"
MAINTAINER = "oe-alliance team"
PACKAGE_ARCH = "${MACHINEBUILD}"

DEPENDS = "python"

require conf/license/license-gplv2.inc

inherit autotools-brokensep gitpkgv pythonnative

SRCREV = "${AUTOREV}"
PV = "${IMAGE_VERSION}+git${SRCPV}"
PKGV = "${IMAGE_VERSION}+git${GITPKGV}"

BRANCH="master"

SRC_URI="git://github.com/oe-alliance/branding-module.git;protocol=git;branch=${BRANCH}"

S = "${WORKDIR}/git"

EXTRA_OECONF = " \
    BUILD_SYS=${BUILD_SYS} \
    HOST_SYS=${HOST_SYS} \
    STAGING_INCDIR=${STAGING_INCDIR} \
    STAGING_LIBDIR=${STAGING_LIBDIR} \
    --with-oever="${OE_VER}" \
    --with-distro="${DISTRO_NAME}" \
    --with-boxtype="${MACHINEBUILD}" \
    --with-brandoem="${BRAND_OEM}" \
    --with-machinebrand="${MACHINE_BRAND}" \
    --with-machinename="${MACHINE_NAME}" \
    --with-machinebuild="${MACHINE}" \
    --with-machinemake="${MACHINEBUILD}" \
    --with-imageversion="${DISTRO_VERSION}" \
    --with-imagebuild="${BUILD_VERSION}" \
    --with-imagedevbuild="${DEVELOPER_BUILD_VERSION}" \
    --with-imagetype="${DISTRO_TYPE}" \
    --with-feedsurl=${DISTRO_FEED_URI} \
    --with-imagedir="${IMAGEDIR}" \
    --with-imagefs="${IMAGE_FSTYPES}" \
    --with-mtdrootfs="${MTD_ROOTFS}" \
    --with-mtdkernel="${MTD_KERNEL}" \
    --with-rootfile="${ROOTFS_FILE}" \
    --with-kernelfile="${KERNEL_FILE}" \
    --with-mkubifs="${MKUBIFS_ARGS}" \
    --with-ubinize="${UBINIZE_ARGS}" \
    --with-driverdate="${DRIVERSDATE}" \
    --with-arch="${DEFAULTTUNE}" \
    "

do_configure_prepend() {
    if [ ! -e ${S}/BoxBranding/remotes/Makefile.in ]; then
        cp ${S}/BoxBranding/remotes/Makefile.am ${S}/BoxBranding/remotes/Makefile.1
        cp ${S}/BoxBranding/remotes/Makefile.am ${S}/BoxBranding/remotes/Makefile.2
        cat ${S}/BoxBranding/remotes/Makefile.1 | grep -v ^if | grep -v ^endif | sed -e 's#SUBDIRS#INDIRS#' > ${S}/BoxBranding/remotes/Makefile.prepend
        cat ${S}/BoxBranding/remotes/Makefile.2 | sed -e 's#SUBDIRS#OUTDIRS#' > ${S}/BoxBranding/remotes/Makefile.append
        mv ${S}/BoxBranding/remotes/Makefile.prepend ${S}/BoxBranding/remotes/Makefile.am
        cat ${S}/BoxBranding/remotes/Makefile.append >> ${S}/BoxBranding/remotes/Makefile.am

        echo 'SUBDIRS = @remove_dirs@' >> ${S}/BoxBranding/remotes/Makefile.am

        rm ${S}/BoxBranding/remotes/Makefile.append ${S}/BoxBranding/remotes/Makefile.1 ${S}/BoxBranding/remotes/Makefile.2

        echo 'AC_SUBST([remove_dirs], ['"'"'$(filter-out $(OUTDIRS),$(INDIRS))'"'"'])' >> ${S}/configure.ac
        cat ${S}/configure.ac | grep -v AC_OUTPUT > ${S}/configure.new
        mv ${S}/configure.new ${S}/configure.ac
        echo 'AC_OUTPUT' >> ${S}/configure.ac
    fi
}

do_install_append() {
    rm -rf ${D}/${libdir}
    rm ${D}/usr/share/enigma2/*.png 2>/dev/null || true
    rm ${D}/usr/share/enigma2/*.jpg 2>/dev/null || true
    install -d ${D}/usr/share/enigma2
}

python populate_packages_prepend() {
    enigma2_remotesdir = '/usr/share/enigma2/rc_models'
    do_split_packages(d, enigma2_remotesdir, '^(\w+)/.*$', 'oe-alliance-branding-remote-%s', '%s (Additional remote for enigma2)', recursive=True, match_path=True, prepend=True, extra_depends='')
}
