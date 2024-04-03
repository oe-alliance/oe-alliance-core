PV = "1.20+11581"
SRCREV = "e4c5382da03fdef06d308a4844c5598e6330c03e"

CAMNAME = "oscam-stable"
SUMMARY:${PN} = "OSCam-stable ${PKGV}"
DESCRIPTION:${PN} = "OSCam Open Source Softcam\n \
- build from a known stable trunk revision\n \"
SRC_URI += "git://repo.or.cz/oscam.git;protocol=git;rev=${SRCREV}"
PR = "r2"
