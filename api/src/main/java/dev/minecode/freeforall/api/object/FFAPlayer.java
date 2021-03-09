package dev.minecode.freeforall.api.object;

import dev.minecode.core.api.object.CorePlayer;

public interface FFAPlayer {

    /**
     * Reloads the current instance of FFAPlayer
     *
     * @return true if successful
     */
    boolean reload();

    /**
     * Saves all player's data to storage (file or database)
     *
     * @return true if successful
     */
    boolean save();

    /**
     * Gets the CorePlayer
     *
     * @return inherent CorePlayer
     */
    CorePlayer getCorePlayer();

    /**
     * Gets the position of FFAPlayer in ranking based of the points
     *
     * @return rank of FFAPlayer
     */
    int getRank();

    /**
     * Gets the FFAPlayer's number of points (e. g. you get by killing)
     *
     * @return points of FFAPlayer
     */
    int getPoints();

    /**
     * Sets the FFAPlayer's number of points in storage (file or database)
     *
     * @param points number of points set to the player
     * @return true if successful
     */
    boolean setPoints(int points);

    /**
     * Adds to the current FFAPlayer's number of points this amount in storage (file or database)
     *
     * @param points number of points add to the player
     * @return true if successful
     */
    boolean addPoints(int points);

    /**
     * Removes this number of points from the current FFAPlayer's number of points in storage (file or database)
     *
     * @param points number of points remove from the player
     * @return true if successful
     */
    boolean removePoints(int points);

    /**
     * Gets the FFAPlayer's number of kills
     *
     * @return kills of FFAPlayer
     */
    int getKills();

    /**
     * Sets the FFAPlayer's number of kills in storage (file or database)
     *
     * @param kills number of kills set to the player
     * @return true if successful
     */
    boolean setKills(int kills);

    /**
     * Adds to the current FFAPlayer's number of kills this amount in storage (file or database)
     *
     * @param kills number of kills add to the player
     * @return true if successful
     */
    boolean addKills(int kills);

    /**
     * Removes this number of kills from the current FFAPlayer's number of kills in storage (file or database)
     *
     * @param kills number of kills remove from the player
     * @return true if successful
     */
    boolean removeKills(int kills);

    /**
     * Gets the FFAPlayer's number of deaths
     *
     * @return deaths of FFAPlayer
     */
    int getDeaths();

    /**
     * Sets the FFAPlayer's number of deaths in storage (file or database)
     *
     * @param deaths number of deaths set to the player
     * @return true if successful
     */
    boolean setDeaths(int deaths);

    /**
     * Adds to the current FFAPlayer's number of deaths this amount in storage (file or database)
     *
     * @param deaths number of deaths add to the player
     * @return true if successful
     */
    boolean addDeaths(int deaths);

    /**
     * Removes this number of deaths from the current FFAPlayer's number of deaths in storage (file or database)
     *
     * @param deaths number of deaths remove from the player
     * @return true if successful
     */
    boolean removeDeaths(int deaths);

    /**
     * Gets the kills per death of the FFAPlayer (-> K/D), rounded to two decimal points
     * K/D cannot be <0
     *
     * @return kills per deaths
     */
    double getKD();

}
