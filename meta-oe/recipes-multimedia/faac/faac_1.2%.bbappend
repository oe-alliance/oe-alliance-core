FILES:${BPN} = " ${bindir}/faac "
FILES:lib${BPN} = " ${libdir}/*.so.*"
FILES:lib${BPN}-dev = " \
    ${includedir} \
    ${libdir}/*.so \
    ${libdir}/*.la \
"