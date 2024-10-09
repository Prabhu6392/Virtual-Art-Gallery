package main;

import dao.VirtualArtGalleryImpl;
import entity.Artwork;
import exception.ArtWorkNotFoundException;

import java.util.List;
import java.util.Scanner;

public class MainModule {
    public static void main(String[] args) {
        VirtualArtGalleryImpl galleryService = new VirtualArtGalleryImpl();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("Welcome to the Virtual Art Gallery!");
            System.out.println("1. Add Artwork");
            System.out.println("2. Update Artwork");
            System.out.println("3. Remove Artwork");
            System.out.println("4. Get Artwork by ID");
            System.out.println("5. Search Artworks");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    // Adding artwork
                    System.out.print("Enter title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter description: ");
                    String description = scanner.nextLine();
                    System.out.print("Enter creation date (YYYY-MM-DD): ");
                    String creationDate = scanner.nextLine();
                    System.out.print("Enter medium: ");
                    String medium = scanner.nextLine();
                    System.out.print("Enter image URL: ");
                    String imageURL = scanner.nextLine();
                    System.out.print("Enter artist ID: ");
                    int artistID = scanner.nextInt();

                    Artwork artwork = new Artwork(0, title, description, creationDate, medium, imageURL, artistID);
                    if (galleryService.addArtwork(artwork)) {
                        System.out.println("Artwork added successfully!");
                    } else {
                        System.out.println("Failed to add artwork.");
                    }
                    break;

                case 2:
                    // Update artwork logic
                    System.out.print("Enter artwork ID to update: ");
                    int artworkIDToUpdate = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    try {
                        // Fetch the existing artwork by ID
                        Artwork existingArtwork = galleryService.getArtworkById(artworkIDToUpdate);

                        System.out.println("Current Title: " + existingArtwork.getTitle());
                        System.out.print("Enter new title (leave empty to keep current): ");
                        String newTitle = scanner.nextLine();
                        if (newTitle.isEmpty()) {
                            newTitle = existingArtwork.getTitle(); // Keep current title if input is empty
                        }

                        System.out.println("Current Description: " + existingArtwork.getDescription());
                        System.out.print("Enter new description (leave empty to keep current): ");
                        String newDescription = scanner.nextLine();
                        if (newDescription.isEmpty()) {
                            newDescription = existingArtwork.getDescription(); // Keep current description if input is empty
                        }

                        System.out.println("Current Creation Date: " + existingArtwork.getCreationDate());
                        System.out.print("Enter new creation date (YYYY-MM-DD) (leave empty to keep current): ");
                        String newCreationDate = scanner.nextLine();
                        if (newCreationDate.isEmpty()) {
                            newCreationDate = existingArtwork.getCreationDate(); // Keep current date if input is empty
                        }

                        System.out.println("Current Medium: " + existingArtwork.getMedium());
                        System.out.print("Enter new medium (leave empty to keep current): ");
                        String newMedium = scanner.nextLine();
                        if (newMedium.isEmpty()) {
                            newMedium = existingArtwork.getMedium(); // Keep current medium if input is empty
                        }

                        System.out.println("Current Image URL: " + existingArtwork.getImageURL());
                        System.out.print("Enter new image URL (leave empty to keep current): ");
                        String newImageURL = scanner.nextLine();
                        if (newImageURL.isEmpty()) {
                            newImageURL = existingArtwork.getImageURL(); // Keep current image URL if input is empty
                        }

                        // Create an updated Artwork object
                        Artwork updatedArtwork = new Artwork(
                                artworkIDToUpdate,  // Use the existing artwork ID
                                newTitle,
                                newDescription,
                                newCreationDate,
                                newMedium,
                                newImageURL,
                                existingArtwork.getArtistID() // Keep the current ArtistID
                        );

                        // Update artwork in the gallery
                        if (galleryService.updateArtwork(updatedArtwork)) {
                            System.out.println("Artwork updated successfully!");
                        } else {
                            System.out.println("Failed to update artwork.");
                        }
                    } catch (ArtWorkNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 3:
                    System.out.print("Enter artwork ID to remove: ");
                    int artworkIDToRemove = scanner.nextInt();
                    try {
                        if (galleryService.removeArtwork(artworkIDToRemove)) {
                            System.out.println("Artwork removed successfully!");
                        }
                    } catch (ArtWorkNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 4:
                    System.out.print("Enter artwork ID: ");
                    int artworkID = scanner.nextInt();
                    try {
                        Artwork foundArtwork = galleryService.getArtworkById(artworkID);
                        System.out.println("Artwork Found: " + foundArtwork.getTitle());
                    } catch (ArtWorkNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 5:
                    System.out.print("Enter keyword to search: ");
                    String keyword = scanner.nextLine();
                    List<Artwork> searchResults = galleryService.searchArtworks(keyword);
                    if (searchResults.isEmpty()) {
                        System.out.println("No artworks found.");
                    } else {
                        System.out.println("Search Results:");
                        for (Artwork art : searchResults) {
                            System.out.println(art.getTitle());
                        }
                    }
                    break;

                case 6:
                    System.out.println("Exiting the application. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 6);

        scanner.close();
    }
}
