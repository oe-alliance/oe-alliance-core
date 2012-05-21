# Script to fix some configure package getting run too late
opkg configure update-modules

# suicide
rm -f /etc/rcS.d/S*early-configure.sh
