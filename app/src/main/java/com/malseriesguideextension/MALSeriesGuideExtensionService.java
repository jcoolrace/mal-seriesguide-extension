package com.malseriesguideextension;

import android.content.Intent;

import com.battlelancer.seriesguide.api.Action;
import com.battlelancer.seriesguide.api.Episode;
import com.battlelancer.seriesguide.api.SeriesGuideExtension;

public class MALSeriesGuideExtensionService extends SeriesGuideExtension {
    public MALSeriesGuideExtensionService() {
        super("MALSeriesGuideExtension");
    }

    /**
     * This is called by SeriesGuide when it wants to grab an action for the app. We create an
     * intent that will take the user to SearchActivity, where it can take care of searching MAL
     * and getting the user further to where they want to go.
     *
     * @param episodeIdentifier The episode identifier the extension should include with the action
     *                          to be published.
     * @param episode Information about the episode being displayed.
     */
    @Override
    protected void onRequest(int episodeIdentifier, Episode episode) {
        // Get show's title and season to pass to SearchActivity
        String query = episode.getShowTitle();
        if (episode.getSeason() > 1) {
            query = query.concat(" " + getString(R.string.function_word_season) + " " + episode.getSeason());
        }

        // Create intent
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra(SearchActivity.SEARCH_QUERY, query);

        // Publish action to SeriesGuide
        publishAction(new Action.Builder(getString(R.string.action_label_episode), episodeIdentifier)
                        .viewIntent(intent).build());
    }
}
