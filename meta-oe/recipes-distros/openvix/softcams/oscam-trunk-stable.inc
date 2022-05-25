#SRCREV = "11581"
SRCREV = "${AUTOREV}"
CAMNAME = "oscam-stable"
SUMMARY:${PN} = "OSCam-stable ${PKGV}"
DESCRIPTION:${PN} = "OSCam Open Source Softcam\n \
- build from a known stable trunk revision\n \"
#SRC_URI += "svn://svn.streamboard.tv/oscam;protocol=https;module=trunk;scmdata=keep;rev=${SRCREV}"
SRC_URI += "git://github.com/Huevos/oscam.git;protocol=https;branch=master"
