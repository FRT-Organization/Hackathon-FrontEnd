package solutions.frt.trashman.Services;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import solutions.frt.trashman.Models.Village;

public interface VillageClient {
    @GET("/village/findall")
    Call<List<Village>> fetchVillages();
}
