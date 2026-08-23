FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://autoheader-nonfatal-warnings.patch"

# autoconf 2.73 runs the man page wrappers as exec "${PERL-perl}". PERL is set to
# the two words /usr/bin/env perl here, so the quoted form looks for a command of
# that literal name and help2man ends up without any --help output.
do_configure:prepend:class-nativesdk() {
    sed -i 's|exec "\($[{]PERL-perl[}]\)"|exec \1|' ${S}/man/*.w
}
