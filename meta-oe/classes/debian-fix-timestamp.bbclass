#
# debian-fix-timestamp.bbclass
#
# Set time stamp of each file to its commit date.
# git stores no time stamp information. It causes various
# build sequences and sometimes it failed because of
# various reasons. The following steps fix this unstable
# behavior by always using commit date for timestamps.
#

addtask debian_fix_timestamp after do_unpack before do_debian_patch
do_debian_fix_timestamp[dirs] = "${DEBIAN_UNPACK_DIR}"
do_debian_fix_timestamp() {
	DIRNAME=$(basename $${DEBIAN_UNPACK_DIR})
	git ls-files | sort > ${WORKDIR}/.git-files.${DIRNAME}
	for ex in ${DEBIAN_GIT_TSFIX_EXCLUDES}; do
		ex_conv=$(echo ${ex} | sed "s@/@\\\\/@g")
		echo "excluded from ${WORKDIR}/.git-files.${DIRNAME}: ${ex_conv}"
		sed -i "/${ex_conv}/d" ${WORKDIR}/.git-files.${DIRNAME}
	done
	while read file; do
		[ ! -L "$file" ] || continue
		# Get commit time from git
		time=$(git log -n 1 --pretty=format:%ci "$file")
		stamp=$(date -d "$time" +"%y%m%d%H%M.%S")
		touch -t $stamp "$file"
	done < ${WORKDIR}/.git-files.${DIRNAME}
}

EXPORT_FUNCTIONS do_debian_fix_timestamp
