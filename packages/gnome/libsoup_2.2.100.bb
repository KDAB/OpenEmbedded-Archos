DESCRIPTION = "An HTTP library implementation in C"
LICENSE = "GPL"
SECTION = "x11/gnome/libs"
DEPENDS = "glib-2.0 gnutls libxml2"

SRC_URI = "http://ftp.gnome.org/pub/GNOME/sources/${PN}/2.2/${PN}-${PV}.tar.bz2"

inherit autotools pkgconfig

do_stage() {
	rm -f ${PKG_CONFIG_PATH}/libsoup*
	autotools_stage_all
	ln -sf ${PKG_CONFIG_PATH}/libsoup.pc ${PKG_CONFIG_PATH}/libsoup-2.2.pc
}

FILES_${PN} = "${libdir}/lib*.so.*"
FILES_${PN}-dev = "${includedir}/ ${libdir}/"
FILES_${PN}-doc = "${datadir}/"