SUMMARY = "Obsolete version of Secure Socket Layer"
SUMMARY = "Secure Socket Layer (SSL) binary and related cryptographic tools."
HOMEPAGE = "http://www.openssl.org/"
BUGTRACKER = "http://www.openssl.org/news/vulnerabilities.html"
SECTION = "libs/network"

INC_PR = "r18"

# "openssl | SSLeay" dual license
LICENSE = "openssl"
LIC_FILES_CHKSUM = "file://LICENSE;md5=f9a8f968107345e0b75aa8c2ecaa7ec8"

DEPENDS = "hostperl-runtime-native"

SRC_URI = "http://www.openssl.org/source/openssl-${PV}.tar.gz \
           file://find.pl \
		   file://multilib.patch \
          "

S = "${WORKDIR}/openssl-${PV}"

#AR_append = " r"
CFLAG = "${@base_conditional('SITEINFO_ENDIANNESS', 'le', '-DL_ENDIAN', '-DB_ENDIAN', d)} \
	-DTERMIO ${FULL_OPTIMIZATION} -Wall"

# Avoid binaries being marked as requiring an executable stack (which causes 
# issues with SELinux on the host)
CFLAG_append_virtclass-native = " -Wa,--noexecstack"

# -02 does not work on mipsel: ssh hangs when it tries to read /dev/urandom
CFLAG_mtx-1 := "${@'${CFLAG}'.replace('-O2', '')}"
CFLAG_mtx-2 := "${@'${CFLAG}'.replace('-O2', '')}"

export DIRS = "crypto ssl apps"
export EX_LIBS = "-lgcc -ldl"
export AS = "${CC} -c"

inherit pkgconfig siteinfo

PACKAGES =+ "libcrypto-old libssl-old"
FILES_libcrypto-old = "${base_libdir}/libcrypto${SOLIBS}"
FILES_libssl-old = "${libdir}/libssl.so.*"
FILES_${PN}-dev += "${base_libdir}/libcrypto${SOLIBSDEV}"

INSANE_SKIP_libcrypto-old += "ldflags"
INSANE_SKIP_libssl-old += "ldflags"

PROVIDES =+ " libcrypto0.9.8 libssl0.9.8"
RPROVIDES_libcrypto-old = "libcrypto0.9.8"
RPROVIDES_libssl-old = "libssl0.9.8"

do_configure_prepend() {
  cp ${WORKDIR}/find.pl ${S}/util/find.pl
}

do_configure_prepend_darwin () {
	sed -i -e '/version-script=openssl\.ld/d' Configure
}

do_configure () {
	cd util
	perl perlpath.pl ${STAGING_BINDIR_NATIVE}
	cd ..
	ln -sf apps/openssl.pod crypto/crypto.pod ssl/ssl.pod doc/

	os=${HOST_OS}
	if [ "x$os" = "xlinux-uclibc" ]; then
		os=linux
	elif [ "x$os" = "xlinux-uclibceabi" ]; then
		os=linux
	elif [ "x$os" = "xlinux-gnueabi" ]; then
		os=linux
	fi
	target="$os-${HOST_ARCH}"
	case $target in
	linux-arm | linux-aarch64)
		target=linux-elf-arm
		;;
	linux-armeb)
		target=linux-elf-armeb
		;;
	linux-sh3)
		target=debian-sh3
		;;
	linux-sh4)
		target=debian-sh4
		;;
	linux-i486)
		target=debian-i386-i486
		;;
	linux-i586 | linux-viac3)
		target=debian-i386-i586
		;;
	linux-i686)
		target=debian-i386-i686/cmov
		;;
	linux-gnux32-x86_64)
		target=linux-x32
		;;
	linux-gnu64-x86_64)
		target=linux-x86_64
		;;
	linux-mips)
		target=debian-mips
		;;
	linux-mipsel)
		target=debian-mipsel
		;;
	linux-powerpc)
		target=linux-ppc
		;;
	linux-gnuspe-powerpc)
		target=linux-ppc
		;;
	linux-powerpc64)
		target=linux-ppc64
		;;
	linux-supersparc)
		target=linux-sparcv8
		;;
	linux-sparc)
		target=linux-sparcv8
		;;
	darwin-i386)
		target=darwin-i386-cc
		;;
	esac
	# inject machine-specific flags
	sed -i -e "s|^\(\"$target\",\s*\"[^:]\+\):\([^:]\+\)|\1:${CFLAG}|g" Configure
        useprefix=${prefix}
        if [ "x$useprefix" = "x" ]; then
                useprefix=/
        fi        
	perl ./Configure ${EXTRA_OECONF} shared --prefix=$useprefix --openssldir=${libdir}/ssl --libdir=`basename ${libdir}` $target
}

do_compile () {
	oe_runmake
}

do_install () {
	oe_runmake INSTALL_PREFIX="${D}" MANDIR="${mandir}" install

	# Moving libcrypto to /lib
	if [ ! ${D}${libdir} -ef ${D}${base_libdir} ]; then
		mkdir -p ${D}/${base_libdir}/
		mv ${D}${libdir}/libcrypto* ${D}${base_libdir}/
	fi

	# Get rid of everything except the bare .so files. We don't want anything
	# to link to this version ever!
	rm -rf ${D}${libdir}/ssl ${D}${includedir} ${D}${bindir} ${D}/${libdir}/pkgconfig ${D}${datadir}
	rm -f ${D}${base_libdir}/*.a ${D}${base_libdir}/*.la
	rm -f ${D}/${libdir}/*.a ${D}/${libdir}/*.la
	rm -f ${D}${base_libdir}/*.so ${D}${libdir}/*.so
	rm -rf ${D}${libdir}/engines
}

PR = "${INC_PR}.0"

LIC_FILES_CHKSUM = "file://LICENSE;md5=f9a8f968107345e0b75aa8c2ecaa7ec8"

SRC_URI += "file://debian/ca.patch \
            file://debian/config-hurd.patch;apply=no \
            file://debian/debian-targets.patch \
            file://debian/engines-path.patch \
            file://debian/kfreebsd-pipe.patch;apply=no \
            file://debian/make-targets.patch \
            file://debian/man-dir.patch \
            file://debian/man-section.patch \
            file://debian/no-rpath.patch \
            file://debian/no-symbolic.patch \
            file://debian/pic.patch \
            file://debian/pkg-config.patch \
            file://debian/rc4-amd64.patch \
            file://debian/rehash-crt.patch \
            file://debian/rehash_pod.patch \
            file://debian/shared-lib-ext.patch \
            file://debian/stddef.patch \
            file://debian/version-script.patch \
            file://debian/perl-path.diff \
            file://debian/openssl-0.9.8x-nodocs.patch"

SRC_URI += "file://configure-targets.patch \
            file://shared-libs.patch \
            file://parallel-make-fix.patch"

SRC_URI[md5sum] = "ee17e9bc805c8cc7d0afac3b0ef78eda"
SRC_URI[sha256sum] = "7ce0c7f2c451070b4497ea7ca6f23eba6cef1a56db2e86e433f65926a7bc7497"

EXTRA_OECONF += "no-idea no-mdc2 no-rc5"

PARALLEL_MAKEINST = ""
