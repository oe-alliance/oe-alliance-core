require dags-blindscan-utils.inc

do_install() {
	install -d "${D}/${bindir}"
	install -m 0755 "${UNPACKDIR}/dags_blindscan_${SOC_FAMILY}" "${D}/${bindir}/dags_blindscan"
}

FILES:${PN} = "${bindir}/dags_blindscan"

SRC_URI[md5sum] = "326941a796c21deb8938f714502c7f60"
SRC_URI[sha256sum] = "3c9f208da566cd4bd60766f7884358e8765aa11129a78c79ec5cec7f903b39c6"
