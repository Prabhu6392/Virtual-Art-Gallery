package test;

import dao.VirtualArtGalleryImpl;
import entity.Artwork;
import entity.Gallery;
import exception.ArtWorkNotFoundException;
import exception.GalleryNotFoundException;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class VirtualArtGalleryImplTest {
    private VirtualArtGalleryImpl galleryService;

    @Before
    public void setUp() {
        galleryService = new VirtualArtGalleryImpl();
    }

    // Artwork Management Tests

    @Test
    public void testAddArtwork() {
        Artwork artwork = new Artwork(0, "Test Artwork", "Description of Test Artwork", "2024-01-01", "Acrylic", "testArtwork.jpg", 1);
        assertTrue(galleryService.addArtwork(artwork));
    }

    @Test
    public void testUpdateArtwork() {
        // Assume artwork with ID 1 exists
        Artwork artwork = new Artwork(1, "Updated Artwork", "Updated Description", "2024-01-02", "Oil", "updatedArtwork.jpg", 1);
        assertTrue(galleryService.updateArtwork(artwork));
    }

    @Test
    public void testRemoveArtwork() {
        // Assume artwork with ID 2 exists
        try {
            assertTrue(galleryService.removeArtwork(2));
        } catch (ArtWorkNotFoundException e) {
            fail("Artwork not found: " + e.getMessage());
        }
    }

    @Test
    public void testGetArtworkById() {
        try {
            Artwork artwork = galleryService.getArtworkById(1); // Assume this artwork exists
            assertNotNull(artwork);
            assertEquals(1, artwork.getArtworkID());
            assertEquals("Test Artwork", artwork.getTitle()); // Adjust according to expected values
        } catch (ArtWorkNotFoundException e) {
            fail("Artwork not found: " + e.getMessage());
        }
    }

    @Test
    public void testSearchArtworks() {
        List<Artwork> artworks = galleryService.searchArtworks("Test");
        assertFalse(artworks.isEmpty());
        assertTrue(artworks.stream().anyMatch(a -> a.getTitle().contains("Test")));
    }

    // Gallery Management Tests

    @Test
    public void testAddGallery() {
        Gallery gallery = new Gallery(0, "Test Gallery", "Description of Test Gallery", "Location", 1, "9 AM - 5 PM");
        assertTrue(galleryService.addGallery(gallery));
    }

    @Test
    public void testUpdateGallery() {
        // Assume gallery with ID 1 exists
        Gallery gallery = new Gallery(1, "Updated Gallery", "Updated Description", "New Location", 1, "10 AM - 6 PM");
        assertTrue(galleryService.updateGallery(gallery));
    }

    @Test
    public void testRemoveGallery() {
        // Assume gallery with ID 2 exists
        try {
            assertTrue(galleryService.removeGallery(1));
        } catch (GalleryNotFoundException e) {
            fail("Gallery not found: " + e.getMessage());
        }
    }

    @Test
    public void testSearchGalleries() {
        List<Gallery> galleries = galleryService.searchGalleries("Test");
        assertFalse(galleries.isEmpty());
        assertTrue(galleries.stream().anyMatch(g -> g.getName().contains("Test")));
    }

    @Test(expected = ArtWorkNotFoundException.class)
    public void testRemoveNonExistentArtwork() throws ArtWorkNotFoundException {
        // Attempt to remove an artwork that does not exist
        galleryService.removeArtwork(999); // Assuming ID 999 does not exist
    }

    @Test(expected = GalleryNotFoundException.class)
    public void testRemoveNonExistentGallery() throws GalleryNotFoundException {
        // Attempt to remove a gallery that does not exist
        galleryService.removeGallery(999); // Assuming ID 999 does not exist
    }
}
