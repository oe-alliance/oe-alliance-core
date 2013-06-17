do_deploy_append() {
	install -m 0644 enigma2-plugin-extensions-backupsuite_11.1r1_mips32el.ipk ${WORKDIR}/deploy-ipks/3rdparty
	install -m 0644 enigma2-plugin-extensions-xtrendforum_0.1_mips32el.ipk ${WORKDIR}/deploy-ipks/3rdparty
}
