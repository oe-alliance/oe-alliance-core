SUMMARY = "Secure Socket Layer"
DESCRIPTION = "Secure Socket Layer (SSL) binary and related cryptographic tools."
HOMEPAGE = "http://www.openssl.org/"
BUGTRACKER = "http://www.openssl.org/news/vulnerabilities.html"
SECTION = "libs/network"

# "openssl | SSLeay" dual license
LICENSE = "OpenSSL"
LIC_FILES_CHKSUM = "file://LICENSE;md5=f475368924827d06d4b416111c8bdb77"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

PACKAGE_ARCH = "${MACHINE_ARCH}"

DEPENDS = "hostperl-runtime-native"
DEPENDS:append:class-target = " openssl-native"

PROVIDES += "libcrypto1.0.2 libssl1.0.2 openssl-conf1.0.2 openssl1.0.2"
RPROVIDES:libcrypto10 ="libcrypto1.0.2"
RPROVIDES:libssl10 ="libssl1.0.2"
RPROVIDES:openssl-conf10 ="openssl-conf1.0.2"
RPROVIDES:${PN} ="openssl1.0.2"

SRC_URI = "http://www.openssl.org/source/openssl-${PV}.tar.gz \
           file://run-ptest \
           file://openssl-c_rehash.sh \
           file://configure-targets.patch \
           file://shared-libs.patch \
           file://oe-ldflags.patch \
           file://engines-install-in-libdir-ssl.patch \
           file://debian1.0.2/block_diginotar.patch \
           file://debian1.0.2/block_digicert_malaysia.patch \
           file://debian/c_rehash-compat.patch \
           file://debian/debian-targets.patch \
           file://debian/man-dir.patch \
           file://debian/man-section.patch \
           file://debian/no-rpath.patch \
           file://debian/no-symbolic.patch \
           file://debian/pic.patch \
           file://debian1.0.2/version-script.patch \
           file://debian1.0.2/soname.patch \
           file://openssl_fix_for_x32.patch \
           file://openssl-fix-des.pod-error.patch \
           file://Makefiles-ptest.patch \
           file://ptest-deps.patch \
           file://ptest_makefile_deps.patch \
           file://configure-musl-target.patch \
           file://parallel.patch \
           file://Use-SHA256-not-MD5-as-default-digest.patch \
           file://0001-Fix-build-with-clang-using-external-assembler.patch \
           file://0001-openssl-force-soft-link-to-avoid-rare-race.patch \
           file://0001-allow-manpages-to-be-disabled.patch \
           file://0001-Fix-BN_LLONG-breakage.patch \
           file://0001-Fix-DES_LONG-breakage.patch \
           file://fix_openssl_100_version.patch \
           file://fix_openssl_100_version_jethro.patch \
           "

SRC_URI:append:class-target = " \
           file://reproducible-cflags.patch \
           file://reproducible-mkbuildinf.patch \
           "

SRC_URI:append:class-nativesdk = " \
           file://environment.d-openssl.sh \
           "

SRC_URI[md5sum] = "cdc2638f789ecc2db2c91488265686c1"
SRC_URI[sha256sum] = "ecd0c6ffb493dd06707d38b14bb4d8c2288bb7033735606569d8f90f89669d16"

S = "${WORKDIR}/openssl-${PV}"

UPSTREAM_CHECK_REGEX = "openssl-(?P<pver>1\.0.+)\.tar"

inherit pkgconfig siteinfo lib_package multilib_header ptest manpages upx-compress

PACKAGECONFIG ?= "cryptodev-linux"
PACKAGECONFIG:class-native = ""
PACKAGECONFIG:class-nativesdk = ""

PACKAGECONFIG[disable-weak-ciphers] = "no-des no-ec no-ecdh no-ecdsa no-md2 no-mdc2,,,"
PACKAGECONFIG[cryptodev-linux] = "-DHAVE_CRYPTODEV -DUSE_CRYPTODEV_DIGESTS,,cryptodev-linux"
PACKAGECONFIG[manpages] = ",,,"
PACKAGECONFIG[perl] = ",,,"

# Remove this to enable SSLv3. SSLv3 is defaulted to disabled due to the POODLE
# vulnerability
EXTRA_OECONF = "no-ssl3"

EXTRA_OEMAKE = "${@bb.utils.contains('PACKAGECONFIG', 'manpages', '', 'OE_DISABLE_MANPAGES=1', d)}"

export OE_LDFLAGS = "${LDFLAGS}"

TERMIO ?= "-DTERMIO"
TERMIO:libc-musl = "-DTERMIOS"
EXTRA_OECONF:append:libc-musl:powerpc64 = " no-asm"

CFLAG = "${@oe.utils.conditional('SITEINFO_ENDIANNESS', 'le', '-DL_ENDIAN', '-DB_ENDIAN', d)} \
         ${TERMIO} ${CFLAGS} -Wall"

# Avoid binaries being marked as requiring an executable stack since they don't
# (and it causes issues with SELinux)
CFLAG += "-Wa,--noexecstack"

CFLAG:append:class-native = " -fPIC"

do_configure () {
	# The crypto_use_bigint patch means that perl's bignum module needs to be
	# installed, but some distributions (for example Fedora 23) don't ship it by
	# default.  As the resulting error is very misleading check for bignum before
	# building.
	if ! perl -Mbigint -e true; then
		bbfatal "The perl module 'bignum' was not found but this is required to build openssl.  Please install this module (often packaged as perl-bignum) and re-run bitbake."
	fi

	ln -sf apps/openssl.pod crypto/crypto.pod ssl/ssl.pod doc/

	os=${HOST_OS}
	case $os in
	linux-gnueabi |\
	linux-gnuspe |\
	linux-musleabi |\
	linux-muslspe |\
	linux-musl )
		os=linux
		;;
	*)
		;;
	esac
	target="$os-${HOST_ARCH}"
	case $target in
	linux-arm)
		target=linux-armv4
		;;
	linux-armeb)
		target=linux-elf-armeb
		;;
	linux-aarch64*)
		target=linux-aarch64
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
	linux-gnux32-x86_64 | linux-muslx32-x86_64 )
		target=linux-x32
		;;
	linux-gnu64-x86_64)
		target=linux-x86_64
		;;
	linux-gnun32-mips*el)
		target=debian-mipsn32el
		;;
	linux-gnun32-mips*)
		target=debian-mipsn32
		;;
	linux-mips*64*el)
		target=debian-mips64el
		;;
	linux-mips*64*)
		target=debian-mips64
		;;
	linux-mips*el)
		target=debian-mipsel
		;;
	linux-mips*)
		target=debian-mips
		;;
	linux-microblaze* | linux-nios2* | linux-gnu*ilp32** | linux-arc*)
		target=linux-generic32
		;;
	linux-powerpc)
		target=linux-ppc
		;;
	linux-powerpc64)
		target=linux-ppc64
		;;
	linux-riscv32)
		target=linux-generic32
		;;
	linux-riscv64)
		target=linux-generic64
		;;
	linux-sparc | linux-supersparc)
		target=linux-sparcv8
		;;
	esac

	# inject machine-specific flags
	sed -i -e "s|^\(\"$target\",\s*\"[^:]\+\):\([^:]\+\)|\1:${CFLAG}|g" Configure

	useprefix=${prefix}
	if [ "x$useprefix" = "x" ]; then
		useprefix=/
	fi
	libdirleaf="$( echo "${libdir}" | sed "s:^$useprefix/*::" )"
	perl ./Configure ${EXTRA_OECONF} ${PACKAGECONFIG_CONFARGS} shared --prefix=$useprefix --openssldir=${libdir}/ssl --libdir=$libdirleaf $target
}

do_compile () {
	oe_runmake depend
	oe_runmake
}

do_compile:class-target () {
	sed -i 's/\((OPENSSL=\)".*"/\1"openssl"/' Makefile
	oe_runmake depend
	cc_sanitized=$(echo "${CC} ${CFLAG}" | sed -e 's,--sysroot=${STAGING_DIR_TARGET},,g' -e 's|${DEBUG_PREFIX_MAP}||g' -e 's/[ \t]\+/ /g')
	oe_runmake CC_INFO="$cc_sanitized"
}

do_compile_ptest () {
	oe_runmake buildtest
}

do_install () {
	# Create ${D}/${prefix} to fix parallel issues
	mkdir -p ${D}/${prefix}/

	oe_runmake INSTALL_PREFIX="${D}" MANDIR="${mandir}" install

	oe_libinstall -so libcrypto ${D}${libdir}
	oe_libinstall -so libssl ${D}${libdir}

	install -d ${D}${includedir}
	cp --dereference -R include/openssl ${D}${includedir}

	oe_multilib_header openssl/opensslconf.h

	install -Dm 0755 ${UNPACKDIR}/openssl-c_rehash.sh ${D}${bindir}/c_rehash
	sed -i -e 's,/etc/openssl,${sysconfdir}/ssl,g' ${D}${bindir}/c_rehash

	if [ "${@bb.utils.filter('PACKAGECONFIG', 'perl', d)}" ]; then
		sed -i -e '1s,.*,#!${bindir}/env perl,' ${D}${libdir}/ssl/misc/CA.pl
		sed -i -e '1s,.*,#!${bindir}/env perl,' ${D}${libdir}/ssl/misc/tsget
	else
		rm -f ${D}${libdir}/ssl/misc/CA.pl ${D}${libdir}/ssl/misc/tsget
	fi

	# Create SSL structure for packages such as ca-certificates which
	# contain hard-coded paths to /etc/ssl. Debian does the same.
	install -d ${D}${sysconfdir}/ssl
	mv ${D}${libdir}/ssl/certs \
	   ${D}${libdir}/ssl/private \
	   ${D}${libdir}/ssl/openssl.cnf \
	   ${D}${sysconfdir}/ssl/

	# Although absolute symlinks would be OK for the target, they become
	# invalid if native or nativesdk are relocated from sstate.
	ln -sf ${@oe.path.relative('${libdir}/ssl', '${sysconfdir}/ssl/certs')} ${D}${libdir}/ssl/certs
	ln -sf ${@oe.path.relative('${libdir}/ssl', '${sysconfdir}/ssl/private')} ${D}${libdir}/ssl/private
	ln -sf ${@oe.path.relative('${libdir}/ssl', '${sysconfdir}/ssl/openssl.cnf')} ${D}${libdir}/ssl/openssl.cnf

	# Rename man pages to prefix openssl10-*
	for f in `find ${D}${mandir} -type f`; do
	    mv $f $(dirname $f)/openssl10-$(basename $f)
	done
	for f in `find ${D}${mandir} -type l`; do
	    ln_f=`readlink $f`
	    rm -f $f
	    ln -s openssl10-$ln_f $(dirname $f)/openssl10-$(basename $f)
	done
}

do_install:append:class-native () {
	create_wrapper ${D}${bindir}/openssl \
	    OPENSSL_CONF=${libdir}/ssl/openssl.cnf \
	    SSL_CERT_DIR=${libdir}/ssl/certs \
	    SSL_CERT_FILE=${libdir}/ssl/cert.pem \
	    OPENSSL_ENGINES=${libdir}/ssl/engines
}

do_install:append:class-nativesdk () {
	mkdir -p ${D}${SDKPATHNATIVE}/environment-setup.d
	install -m 644 ${UNPACKDIR}/environment.d-openssl.sh ${D}${SDKPATHNATIVE}/environment-setup.d/openssl.sh
}

do_install_ptest () {
	cp -r -L Makefile.org Makefile test ${D}${PTEST_PATH}

	# Replace the path to native perl with the path to target perl
	sed -i 's,^PERL=.*,PERL=${bindir}/perl,' ${D}${PTEST_PATH}/Makefile

	cp Configure config e_os.h ${D}${PTEST_PATH}
	cp -r -L include ${D}${PTEST_PATH}
	ln -sf ${libdir}/libcrypto.a ${D}${PTEST_PATH}
	ln -sf ${libdir}/libssl.a ${D}${PTEST_PATH}
	mkdir -p ${D}${PTEST_PATH}/crypto
	cp crypto/constant_time_locl.h ${D}${PTEST_PATH}/crypto
	cp -r certs ${D}${PTEST_PATH}
	mkdir -p ${D}${PTEST_PATH}/apps
	ln -sf ${libdir}/ssl/misc/CA.sh  ${D}${PTEST_PATH}/apps
	ln -sf ${sysconfdir}/ssl/openssl.cnf ${D}${PTEST_PATH}/apps
	ln -sf ${bindir}/openssl         ${D}${PTEST_PATH}/apps
	cp apps/server.pem              ${D}${PTEST_PATH}/apps
	cp apps/server2.pem             ${D}${PTEST_PATH}/apps
	mkdir -p ${D}${PTEST_PATH}/util
	install util/opensslwrap.sh    ${D}${PTEST_PATH}/util
	install util/shlib_wrap.sh     ${D}${PTEST_PATH}/util
	# Time stamps are relevant for "make alltests", otherwise
	# make may try to recompile binaries. Not only must the
	# binary files be newer than the sources, they also must
	# be more recent than the header files in /usr/include.
	#
	# Using "cp -a" is not sufficient, because do_install
	# does not preserve the original time stamps.
	#
	# So instead of using the original file stamps, we set
	# the current time for all files. Binaries will get
	# modified again later when stripping them, but that's okay.
	touch ${D}${PTEST_PATH}
	find ${D}${PTEST_PATH} -type f -print0 | xargs --verbose -0 touch -r ${D}${PTEST_PATH}

	# exclude binary files or the package won't install
	for d in ssltest_old v3ext x509aux; do
		rm -rf ${D}${libdir}/${BPN}/ptest/test/$d
	done

	# Remove build host references
	sed -i \
	-e 's,--sysroot=${STAGING_DIR_TARGET},,g' \
	-e 's|${DEBUG_PREFIX_MAP}||g' \
	${D}${PTEST_PATH}/Makefile ${D}${PTEST_PATH}/Configure
}

# Add the openssl.cnf file to the openssl-conf package. Make the libcrypto
# package RRECOMMENDS on this package. This will enable the configuration
# file to be installed for both the base openssl package and the libcrypto
# package since the base openssl package depends on the libcrypto package.

PACKAGES =+ "libcrypto10 libssl10 openssl10-conf ${PN}-engines ${PN}-misc"

FILES:libcrypto10 = "${libdir}/libcrypto${SOLIBS}"
FILES:libssl10 = "${libdir}/libssl${SOLIBS}"
FILES:openssl10-conf = "${sysconfdir}/ssl/openssl.cnf"
FILES:${PN}-engines = "${libdir}/ssl/engines/*.so ${libdir}/engines"
FILES:${PN}-misc = "${libdir}/ssl/misc"
FILES:${PN} =+ "${libdir}/ssl/*"
FILES:${PN}:append:class-nativesdk = " ${SDKPATHNATIVE}/environment-setup.d/openssl.sh"
FILES:${PN}-staticdev += "*.a"

CONFFILES:openssl10-conf = "${sysconfdir}/ssl/openssl.cnf"

RRECOMMENDS:libcrypto10 += "openssl10-conf"
RDEPENDS:${PN}-misc = "${@bb.utils.filter('PACKAGECONFIG', 'perl', d)}"
RDEPENDS:${PN}-ptest += "${PN}-misc make perl perl-module-filehandle bc"

BBCLASSEXTEND = "native nativesdk"
PACKAGE_PREPROCESS_FUNCS += "openssl_package_preprocess"

# openssl 1.0 development files and executable binaries clash with openssl 1.1
# files when installed into target rootfs. So we don't put them into
# packages, but they continue to be provided via target sysroot for
# cross-compilation on the host, if some software still depends on openssl 1.0.
openssl_package_preprocess () {
        mv ${PKGD}${libdir}/pkgconfig/libcrypto.pc ${PKGD}${libdir}/pkgconfig/libcrypto10.pc
        mv ${PKGD}${libdir}/pkgconfig/libssl.pc ${PKGD}${libdir}/pkgconfig/libssl10.pc
        mv ${PKGD}${libdir}/pkgconfig/openssl.pc ${PKGD}${libdir}/pkgconfig/openssl10.pc
        mv ${PKGD}${libdir}/libcrypto.a ${PKGD}/libcrypto10.a
        mv ${PKGD}${libdir}/libssl.a ${PKGD}/libssl10.a
        mv ${PKGD}/usr/include/openssl ${PKGD}/usr/include/openssl10
        rm ${PKGD}${libdir}/*.so
        rm ${PKGD}${bindir}/openssl
        rm ${PKGD}${bindir}/c_rehash
        rmdir ${PKGD}${bindir}

}
