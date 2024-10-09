package dao;

import entity.Artwork;
import entity.Gallery;
import exception.ArtWorkNotFoundException;
import exception.GalleryNotFoundException;

import java.util.List;

public interface IVirtualArtGallery {
    // Artwork Management
    boolean addArtwork(Artwork artwork);
    boolean updateArtwork(Artwork artwork);
    boolean removeArtwork(int artworkID) throws ArtWorkNotFoundException; // Add the throws clause
    Artwork getArtworkById(int artworkID) throws ArtWorkNotFoundException;
    List<Artwork> searchArtworks(String keyword);

    // User Favorite Management
    boolean addArtworkToFavorite(int userID, int artworkID);
    boolean removeArtworkFromFavorite(int userID, int artworkID);
    List<Artwork> getUserFavoriteArtworks(int userID);

    // Gallery Management
    boolean addGallery(Gallery gallery);
    boolean updateGallery(Gallery gallery);
    boolean removeGallery(int galleryID) throws GalleryNotFoundException; // Add the throws clause
    List<Gallery> searchGalleries(String keyword);
}
