package com.example.movieapplication.appolloclient;

import android.content.Context;

import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Operation;
import com.apollographql.apollo.api.ResponseField;
import com.apollographql.apollo.cache.normalized.CacheKey;
import com.apollographql.apollo.cache.normalized.CacheKeyResolver;
import org.jetbrains.annotations.NotNull;
import java.util.Map;
import okhttp3.OkHttpClient;

public class ApolloClientConnector {

    private static final String BASE_URL = "http://10.0.2.2:4000/";
    private static ApolloClient apolloClient;

    private static ApolloClient buildApolloClientInstance(Context applicationContext){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();

        CacheKeyResolver cacheKeyResolver = new CacheKeyResolver() {
            @NotNull
            @Override
            public CacheKey fromFieldRecordSet(@NotNull ResponseField field, @NotNull Map<String, Object> recordSet) {
                String typeName = (String) recordSet.get("__typename");
                if ("User".equals(typeName)) {
                    String userKey = typeName + "." + recordSet.get("login");
                    return CacheKey.from(userKey);
                }
                if (recordSet.containsKey("id")) {
                    String typeNameAndIDKey = recordSet.get("__typename") + "." + recordSet.get("id");
                    return CacheKey.from(typeNameAndIDKey);
                }
                return CacheKey.NO_KEY;
            }

            // Use this resolver to customize the key for fields with variables: eg entry(repoFullName: $repoFullName).
            // This is useful if you want to make query to be able to resolved, even if it has never been run before.
            @NotNull @Override
            public CacheKey fromFieldArguments(@NotNull ResponseField field, @NotNull Operation.Variables variables) {
                return CacheKey.NO_KEY;
            }
        };

         apolloClient = ApolloClient.builder()
                .serverUrl(BASE_URL)
                .okHttpClient(okHttpClient)
                .build();
        return apolloClient;
    }

    public static ApolloClient getApolloClient(Context applicationContext) {
        if ( apolloClient== null) {
            apolloClient = buildApolloClientInstance(applicationContext);
        }
        return apolloClient;
    }

}
