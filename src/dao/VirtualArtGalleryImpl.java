package dao;

import entity.Artwork;
import entity.Gallery;
import util.DBConnUtil;
import exception.ArtWorkNotFoundException;
import exception.GalleryNotFoundException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VirtualArtGalleryImpl implements IVirtualArtGallery {
    private Connection connection;

    public VirtualArtGalleryImpl() {
        connection = DBConnUtil.getConnection();
    }

    // Artwork Management

    @Override
    public boolean addArtwork(Artwork artwork) {
        String query = "INSERT INTO Artwork (title, description, creationDate, medium, imageURL, artistID) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, artwork.getTitle());
            ps.setString(2, artwork.getDescription());
            ps.setString(3, artwork.getCreationDate());
            ps.setString(4, artwork.getMedium());
            ps.setString(5, artwork.getImageURL());
            ps.setInt(6, artwork.getArtistID());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateArtwork(Artwork artwork) {
        String query = "UPDATE Artwork SET title = ?, description = ?, creationDate = ?, medium = ?, imageURL = ?, artistID = ? WHERE artworkID = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, artwork.getTitle());
            ps.setString(2, artwork.getDescription());
            ps.setString(3, artwork.getCreationDate());
            ps.setString(4, artwork.getMedium());
            ps.setString(5, artwork.getImageURL());
            ps.setInt(6, artwork.getArtistID());
            ps.setInt(7, artwork.getArtworkID());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean removeArtwork(int artworkID) throws ArtWorkNotFoundException {
        String query = "DELETE FROM Artwork WHERE artworkID = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, artworkID);
            if (ps.executeUpdate() == 0) {
                throw new ArtWorkNotFoundException("Artwork with ID " + artworkID + " not found.");
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Artwork getArtworkById(int artworkID) throws ArtWorkNotFoundException {
        String query = "SELECT * FROM Artwork WHERE artworkID = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, artworkID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Artwork(
                    rs.getInt("artworkID"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getString("creationDate"),
                    rs.getString("medium"),
                    rs.getString("imageURL"),
                    rs.getInt("artistID")
                );
            } else {
                throw new ArtWorkNotFoundException("Artwork with ID " + artworkID + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Artwork> searchArtworks(String keyword) {
        List<Artwork> artworks = new ArrayList<>();
        String query = "SELECT * FROM Artwork WHERE title LIKE ? OR description LIKE ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                artworks.add(new Artwork(
                    rs.getInt("artworkID"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getString("creationDate"),
                    rs.getString("medium"),
                    rs.getString("imageURL"),
                    rs.getInt("artistID")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return artworks;
    }

    // Gallery Management

    @Override
    public boolean addGallery(Gallery gallery) {
        String query = "INSERT INTO Gallery (name, description, location, curatorID, openingHours) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, gallery.getName());
            ps.setString(2, gallery.getDescription());
            ps.setString(3, gallery.getLocation());
            ps.setInt(4, gallery.getCuratorID());
            ps.setString(5, gallery.getOpeningHours());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateGallery(Gallery gallery) {
        String query = "UPDATE Gallery SET name = ?, description = ?, location = ?, curatorID = ?, openingHours = ? WHERE galleryID = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, gallery.getName());
            ps.setString(2, gallery.getDescription());
            ps.setString(3, gallery.getLocation());
            ps.setInt(4, gallery.getCuratorID());
            ps.setString(5, gallery.getOpeningHours());
            ps.setInt(6, gallery.getGalleryID());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean removeGallery(int galleryID) throws GalleryNotFoundException {
        String query = "DELETE FROM Gallery WHERE galleryID = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, galleryID);
            if (ps.executeUpdate() == 0) {
                throw new GalleryNotFoundException("Gallery with ID " + galleryID + " not found.");
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Gallery> searchGalleries(String keyword) {
        List<Gallery> galleries = new ArrayList<>();
        String query = "SELECT * FROM Gallery WHERE name LIKE ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                galleries.add(new Gallery(
                    rs.getInt("galleryID"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getString("location"),
                    rs.getInt("curatorID"),
                    rs.getString("openingHours")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return galleries;
    }

    // User Favorite Management Methods

    @Override
    public boolean addArtworkToFavorite(int userID, int artworkID) {
        String query = "INSERT INTO User_Favorite_Artwork (UserID, ArtworkID) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, userID);
            ps.setInt(2, artworkID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean removeArtworkFromFavorite(int userID, int artworkID) {
        String query = "DELETE FROM User_Favorite_Artwork WHERE UserID = ? AND ArtworkID = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, userID);
            ps.setInt(2, artworkID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Artwork> getUserFavoriteArtworks(int userID) {
        List<Artwork> favorites = new ArrayList<>();
        String query = "SELECT a.* FROM Artwork a JOIN User_Favorite_Artwork u ON a.artworkID = u.ArtworkID WHERE u.UserID = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                favorites.add(new Artwork(
                    rs.getInt("artworkID"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getString("creationDate"),
                    rs.getString("medium"),
                    rs.getString("imageURL"),
                    rs.getInt("artistID")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return favorites;
    }
}
