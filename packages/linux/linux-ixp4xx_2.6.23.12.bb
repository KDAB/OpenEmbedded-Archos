# The new ethernet driver included in the 2.6.23 version of the nslu2-linux.org kernel patches
# has problems with providing the correct MAC address on the initial DHCP request.
DEFAULT_PREFERENCE = "-1"

require linux.inc
require linux-ixp4xx.inc

VANILLA_VERSION = "2.6.23"
KERNEL_RELEASE = "2.6.23.12"

# If you use a rc, you will need to use this:
#PV = "${VANILLA_VERSION}+${KERNEL_RELEASE}+svnr${SRCREV}"

PV = "${KERNEL_RELEASE}+svnr${SRCREV}"
PR = "r0"

SRC_URI = "${KERNELORG_MIRROR}/pub/linux/kernel/v2.6/linux-${VANILLA_VERSION}.tar.bz2 \
           ${KERNELORG_MIRROR}/pub/linux/kernel/v2.6/patch-${KERNEL_RELEASE}.bz2;patch=1 \
	   svn://svn.nslu2-linux.org/svnroot/kernel/trunk/patches;module=${VANILLA_VERSION};proto=http \
	   file://defconfig-${KERNEL_RELEASE}"

S = "${WORKDIR}/linux-${VANILLA_VERSION}"

# Apply the patches from the nslu2-linux project (after the patches in SRC_URI)
do_postpatch() {
	# Move away OE patches which have been already applied.
	mv ${S}/patches ${S}/patches.oe
	mv .pc .pc.oe

	# Move the NSLU2 patches in place for quilt and apply them.
        mv ${WORKDIR}/${VANILLA_VERSION} ${S}/patches && cd ${S} && quilt push -av

	# Store the NSLU2 patches 
        mv ${S}/patches ${S}/patches.ixp4xx
        mv .pc .pc.ixp4xx

 	# And move back the OE patches
	mv ${S}/patches.oe ${S}/patches
	mv .pc.oe .pc

	# Copy the defconfig into ${WORKDIR}
        mv ${WORKDIR}/defconfig-${KERNEL_RELEASE} ${WORKDIR}/defconfig
}

addtask postpatch after do_patch before do_configure