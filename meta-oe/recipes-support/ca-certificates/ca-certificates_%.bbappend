pkg_postinst:${PN}:class-target () {
    $D${sbindir}/update-ca-certificates --sysroot $D/
}
