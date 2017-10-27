
do_install_append() {
    sed -e 's!# End of file!*                -       nofile          16384\n# End of file!' -i ${D}/${sysconfdir}/security/limits.conf
}
