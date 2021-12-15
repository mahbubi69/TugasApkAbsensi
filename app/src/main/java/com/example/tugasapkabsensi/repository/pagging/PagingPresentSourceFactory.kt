package com.example.tugasapkabsensi.repository.pagging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.tugasapkabsensi.restApi.UserService
import com.example.tugasapkabsensi.restApi.model.GuruMapelModel
import com.example.tugasapkabsensi.value.Value
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
class PagingPresentSourceFactory(
    private val siswaService: UserService,
    private val token: String,
    private val idJurusanKelas: Int,
) : PagingSource<Int, GuruMapelModel>() {
    override fun getRefreshKey(state: PagingState<Int, GuruMapelModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GuruMapelModel> {

        val result = siswaService.getGuruMapel(token, idJurusanKelas)
        val data = result.data ?: emptyList()
        val page = params.key ?: Value.DEFAULT_PAGE_INDEX

        return LoadResult.Page(
            data = data,
            nextKey = if ((result.rowCount / Value.DEFAULT_PAGE_SIZE) < page) null else page + 1,
            prevKey = if (page == Value.DEFAULT_PAGE_INDEX) null else -1,
        )
    }
}