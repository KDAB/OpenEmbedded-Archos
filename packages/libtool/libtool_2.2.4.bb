require libtool.inc

PR = "r12"

DEFAULT_PREFERENCE = "-1"

SRC_URI = "${GNU_MIRROR}/libtool/libtool-${PV}.tar.gz"
S = "${WORKDIR}/libtool-${PV}"

PACKAGES =+ "libltdl libltdl-dev libltdl-dbg"
FILES_${PN} += "${datadir}/aclocal*"
FILES_libltdl = "${libdir}/libltdl.so.*"
FILES_libltdl-dev = "${libdir}/libltdl.* ${includedir}/ltdl.h"
FILES_libltdl-dbg = "${libdir}/.debug/"

inherit autotools

EXTRA_AUTORECONF = "--exclude=libtoolize"

do_stage () {
	autotools_stage_all
}